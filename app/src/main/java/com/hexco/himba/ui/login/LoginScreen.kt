package com.hexco.himba.ui.tabs.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hexco.himba.ui.login.LoginEvent
import com.hexco.himba.ui.login.LoginViewModel
import com.hexco.himba.ui.login.components.LoginButton
import com.hexco.himba.ui.login.components.LoginTextField
import com.hexco.himba.ui.util.LARGE_SPACE
import com.hexco.himba.ui.util.SMALL_SPACE

@Composable
fun LoginScreen(
  viewModel: LoginViewModel = hiltViewModel(),
) {
  val state by viewModel.state.collectAsStateWithLifecycle()

  Box(
    modifier = Modifier
      .padding(vertical = 36.dp, horizontal = 36.dp)
  ) {
    LoginForm(
      username = state.username,
      password = state.password,
      isLoading = state.isLoading,
      isUsernameError = state.usernameError,
      isPasswordError = state.passwordError,
      onUsernameChange = {
        viewModel.onEvent(LoginEvent.OnUsernameChange(it))
      },
      onPasswordChange = {
        viewModel.onEvent(LoginEvent.OnPasswordChange(it))
      },
      onLoginClick = {
        viewModel.onEvent(LoginEvent.OnLoginClick)
      },
      onSignupClick = {
        viewModel.onEvent(LoginEvent.OnSignupClick)
      }
    )
  }
}

@Composable
fun LoginForm(
  username: String,
  onUsernameChange: (String) -> Unit,
  isUsernameError: Boolean,
  password: String,
  onPasswordChange: (String) -> Unit,
  isPasswordError: Boolean,
  onLoginClick: () -> Unit,
  onSignupClick: () -> Unit,
  isLoading: Boolean = false,
) {
  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Text(
      modifier = Modifier.fillMaxWidth(),
      text = "Log In",
      color = Color.White,
      textAlign = TextAlign.Start,
      fontSize = 36.sp,
      fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(LARGE_SPACE.dp))

    LoginTextField(
      leadingIcon = Icons.Default.Person,
      lable = "Username",
      isError = isUsernameError,
      value = username,
      onValueChange = onUsernameChange
    )

    Spacer(modifier = Modifier.height(SMALL_SPACE.dp))

    LoginTextField(
      leadingIcon = Icons.Default.Lock,
      lable = "Password",
      isError = isPasswordError,
      value = password,
      onValueChange = onPasswordChange,
      isPassword = true,
      onDone = onLoginClick
    )

    Spacer(modifier = Modifier.height(LARGE_SPACE.dp))

    LoginButton(
      modifier = Modifier.height(48.dp),
      lable = "Login",
      onClick = onLoginClick,
      isLoading = isLoading
    )

    Spacer(modifier = Modifier.height(LARGE_SPACE.dp))

    Row(
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text("Don't have an account?")
      TextButton(
        onClick = onSignupClick
      ) {
        Text("Sign Up")
      }
    }
  }
}
