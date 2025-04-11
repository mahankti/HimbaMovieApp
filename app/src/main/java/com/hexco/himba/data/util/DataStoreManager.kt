package com.hexco.himba.data.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.hexco.himba.data.local.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(context: Context) {
  private val dataStore = context.dataStore

  companion object {
    private val SESSION_ID = stringPreferencesKey("session_id")
    private val REQUEST_TOKEN = stringPreferencesKey("request_token")
    private val TOKEN_TIMESTAMP = longPreferencesKey("token_timestamp")
  }

  val sessionId: Flow<String> = dataStore.data.map { preferences ->
    preferences[SESSION_ID] ?: ""
  }

  val requestToken: Flow<Pair<String, Long>> = dataStore.data.map { preferences ->
    val token = preferences[REQUEST_TOKEN] ?: ""
    val timestamp = preferences[TOKEN_TIMESTAMP] ?: 0L

    token to timestamp
  }

  suspend fun saveToken(token: String) {
    dataStore.edit { preferences ->
      preferences[REQUEST_TOKEN] = token
      preferences[TOKEN_TIMESTAMP] = System.currentTimeMillis()
    }
  }

  suspend fun saveSessionId(sessionId: String) {
    dataStore.edit { preferences ->
      preferences[SESSION_ID] = sessionId
    }
  }
}
