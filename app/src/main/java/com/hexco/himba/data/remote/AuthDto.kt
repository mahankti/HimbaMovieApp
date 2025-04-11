package com.hexco.himba.data.remote

import com.squareup.moshi.Json

data class TokenDto(
  @Json(name = "request_token") val token: String
)

data class SessionDto(
  val success: Boolean,
  @Json(name = "session_id") val sessionId: String
)

data class Credential(
  val username: String,
  val password: String,
  @Json(name = "request_token") val token: String
)