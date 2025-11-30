package com.example.qazaqpaybank.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qazaqpaybank.data.ApiClient
import com.example.qazaqpaybank.data.RegisterRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _success = MutableStateFlow(false)
    val success: StateFlow<Boolean> = _success

    fun setError(message: String) {
        _error.value = message
    }

    fun register(
        firstName: String,
        lastName: String,
        phone: String,
        email: String,
        password: String,
        iin: String
    ) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                val request = RegisterRequest(
                    firstName = firstName,
                    lastName = lastName,
                    phoneNumber = phone,
                    email = email,
                    password = password,
                    iin = iin
                )

                val response = ApiClient.apiService.register(request)

                if (response.isSuccessful) {
                    _success.value = true
                } else {
                    val errorBody = response.errorBody()?.string()
                    _error.value = "Ошибка регистрации: $errorBody"
                }
            } catch (e: Exception) {
                _error.value = "Ошибка сети: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}
