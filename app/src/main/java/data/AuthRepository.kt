package com.example.qazaqpaybank.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

class AuthRepository(private val context: Context) {

    private val TOKEN_KEY = stringPreferencesKey("jwt_token")

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    val tokenFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[TOKEN_KEY]
    }

    suspend fun login(email: String, password: String): LoginResponse? {
        val response = ApiClient.apiService.login(LoginRequest(email, password))
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun verifyMfa(email: String, code: String): String? {
        val response = ApiClient.apiService.verifyMfa(MfaRequest(email, code))
        return if (response.isSuccessful) response.body()?.token else null
    }
}
