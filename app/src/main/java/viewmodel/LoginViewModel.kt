package com.example.qazaqpaybank.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qazaqpaybank.data.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private var repo: AuthRepository? = null

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val loading = MutableStateFlow(false)
    val error = MutableStateFlow<String?>(null)
    val mfaRequired = MutableStateFlow(false)
    val success = MutableStateFlow(false)

    private val _mfaSuccess = MutableStateFlow(false)
    val mfaSuccess: StateFlow<Boolean> = _mfaSuccess

    fun init(context: Context) {
        if (repo == null) {
            repo = AuthRepository(context)
        }
    }

    fun login() {
        viewModelScope.launch {
            loading.value = true
            error.value = null

            try {
                val response = repo?.login(email.value, password.value)

                if (response != null) {
                    if (response.mfaRequired) {
                        mfaRequired.value = true
                    } else if (response.token != null) {
                        repo?.saveToken(response.token)
                        success.value = true
                    }
                } else {
                    error.value = "Invalid credentials"
                }
            } catch (e: Exception) {
                error.value = "Error: ${e.message}"
            }

            loading.value = false
        }
    }

    fun verifyMfa(email: String, code: String) {
        viewModelScope.launch {
            loading.value = true

            try {
                val token = repo?.verifyMfa(email, code)

                if (token != null) {
                    repo?.saveToken(token)
                    _mfaSuccess.value = true
                } else {
                    error.value = "Invalid MFA code"
                }
            } catch (e: Exception) {
                error.value = "Error: ${e.message}"
            }

            loading.value = false
        }
    }
}
