package com.hexco.himba.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hexco.himba.data.util.DataStoreManager
import com.hexco.himba.domain.repository.ProfileRepository
import com.hexco.himba.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
  private val repository: ProfileRepository,
  private val dataStoreManager: DataStoreManager,
) : ViewModel() {

  val _state = MutableStateFlow(ProfileState())
  val state = _state.asStateFlow()

  init {
    getProfile()
  }

  private fun getProfile() {
    viewModelScope.launch {
      dataStoreManager.sessionId.collect {
        val profile = repository.getProfile(it)
        when (profile) {
          is Resource.Error,
          is Resource.Success -> {
            _state.update {
              it.copy(
                name = profile.data!!.name,
                username = profile.data.username,
                avatarPath = profile.data.avatar.tmdb.avatarPath ?: ""
              )
            }
          }
        }
      }
    }
  }
}