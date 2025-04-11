package com.hexco.himba.ui.login

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hexco.himba.data.util.DataStoreManager
import com.hexco.himba.domain.repository.AuthRepository
import com.hexco.himba.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
  val repository: AuthRepository,
  val dataStoreManager: DataStoreManager,
  @ApplicationContext private val context: Context
) : ViewModel() {
  private val _state = MutableStateFlow(LoginState())
  val state = _state.asStateFlow()

  fun onEvent(event: LoginEvent) {
    when (event) {
      LoginEvent.OnSignupClick -> {
        val signupIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.themoviedb.org/signup"))
        signupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(signupIntent)
      }

      LoginEvent.OnLoginClick -> {
        _state.update {
          it.copy(
            usernameError = false,
            passwordError = false
          )
        }

        val isValid = validateFields()
        if (isValid) {
          viewModelScope.launch {
            _state.update {
              it.copy(
                isLoading = true
              )
            }

            val token = getToken()
            val session = repository.login(username = state.value.username, password = state.value.password, token = token)
            when (session) {
              is Resource.Success -> {
                if (session.data!!.success) {
                  dataStoreManager.saveSessionId(session.data.sessionId)
                  dataStoreManager.saveToken("")
                  _state.update {
                    LoginState()
                  }
                }
              }

              is Resource.Error -> {
                _state.update {
                  it.copy(
                    usernameError = true,
                    passwordError = true,
                    isLoading = false
                  )
                }
              }
            }
          }
        }
      }

      is LoginEvent.OnUsernameChange -> {
        _state.update {
          it.copy(
            username = event.value
          )
        }
      }

      is LoginEvent.OnPasswordChange -> {
        _state.update {
          it.copy(
            password = event.value
          )
        }
      }
    }
  }

  private fun validateFields(): Boolean {
    var userError = false
    var passError = false
    if (state.value.username.isEmpty()) {
      userError = true
    }
    if (state.value.password.isEmpty()) {
      passError = true
    }

    _state.update {
      it.copy(
        usernameError = userError,
        passwordError = passError
      )
    }

    return !userError && !passError
  }

  private suspend fun getToken(): String {
    val tokenData = dataStoreManager.requestToken.first()
    val (savedToken, timestamp) = tokenData

    val doesTokenExist =
      (System.currentTimeMillis() / 60000 - timestamp / 60000) < 60

    return if (doesTokenExist && savedToken.isNotEmpty()) {
      savedToken
    } else {
      when (val response = repository.createToken()) {
        is Resource.Error -> ""
        is Resource.Success -> {
          response.data?.let {
            dataStoreManager.saveToken(it)
            it
          } ?: ""
        }
      }
    }
  }
}