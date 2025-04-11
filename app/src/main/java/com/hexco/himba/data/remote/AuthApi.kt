package com.hexco.himba.data.remote

import com.hexco.himba.data.util.Validation
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface AuthApi {
  @GET("authentication/token/new")
  suspend fun generateToken(
    @HeaderMap header: Map<String, String>
  ): TokenDto

  @POST("authentication/token/validate_with_login")
  suspend fun validateToken(
    @HeaderMap header: Map<String, String>,
    @Body credential: Credential
  ): Validation

  @POST("authentication/session/new")
  suspend fun createSessionId(
    @HeaderMap header: Map<String, String>,
    @Body token: TokenDto
  ): SessionDto
}