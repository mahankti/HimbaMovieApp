package com.hexco.himba.ui.login

data class LoginState(
  val username: String = "",
  val password: String = "",
  val usernameError: Boolean = false,
  val passwordError: Boolean = false,

  val isLoading: Boolean = false,
)
