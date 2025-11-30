package com.example.qazaqpaybank.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

class AuthRepository(private val context: Context) {

    private val tokenKey = stringPreferencesKey("jwt_token")

    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[tokenKey] = token
        }
    }

    suspend fun getToken(): String? {
        return context.dataStore.data.map { prefs ->
            prefs[tokenKey]
        }.firstOrNull()
    }

    suspend fun login(email: String, password: String): LoginResponse? {
        return try {
            Log.d("AuthRepository", "Attempting login for: $email")

            val response = ApiClient.apiService.login(LoginRequest(email, password))

            Log.d("AuthRepository", "Response code: ${response.code()}")
            Log.d("AuthRepository", "Response body: ${response.body()}")

            if (response.isSuccessful) {
                val body = response.body()

                // ФИКС: Если токена нет и есть сообщение про MFA - значит MFA нужен!
                if (body?.token == null && body?.message?.contains("MFA", ignoreCase = true) == true) {
                    Log.d("AuthRepository", "MFA required detected from message")
                    // Возвращаем исправленный ответ
                    return LoginResponse(
                        token = null,
                        email = email,
                        message = "Требуется MFA",
                        mfaRequired = true
                    )

                }

                body
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("AuthRepository", "Error body: $errorBody")
                null
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Exception during login", e)
            e.printStackTrace()
            null
        }
    }


    suspend fun verifyMfa(email: String, code: String): String? {
        return try {
            Log.d("AuthRepository", "Verifying MFA for: $email with code: $code")

            val response = ApiClient.apiService.verifyMfa(MfaRequest(email, code))

            Log.d("AuthRepository", "MFA Response code: ${response.code()}")
            Log.d("AuthRepository", "MFA Response body: ${response.body()}")

            if (response.isSuccessful) {
                response.body()?.token
            } else {
                Log.e("AuthRepository", "MFA Error: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Exception during MFA", e)
            null
        }
    }
}
