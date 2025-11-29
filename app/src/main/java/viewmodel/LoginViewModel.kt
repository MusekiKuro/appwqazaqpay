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

            // ВРЕМЕННЫЙ ДОПУСК ДЛЯ ДЕМО
            if (email.value.isNotEmpty() && password.value.isNotEmpty()) {
                mfaRequired.value = true
                loading.value = false
                return@launch
            }

            // дальше твой настоящий вызов репозитория...
        }
    }


    fun verifyMfa(email: String, code: String) {
        viewModelScope.launch {
            loading.value = true
            error.value = null

            // ВРЕМЕННЫЙ БАЙПАС - любой 6-значный код работает!
            if (code.length == 6) {
                repo?.saveToken("demo_jwt_token_${System.currentTimeMillis()}")
                _mfaSuccess.value = true
                loading.value = false
                return@launch
            }

            error.value = "Code must be 6 digits"
            loading.value = false
        }
    }
}
