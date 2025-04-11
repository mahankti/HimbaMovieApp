package com.hexco.himba.domain.repository

import com.hexco.himba.data.remote.SessionDto
import com.hexco.himba.domain.util.Resource

interface AuthRepository {
  suspend fun createToken(): Resource<String>
  suspend fun login(username: String, password: String, token: String): Resource<SessionDto>
}