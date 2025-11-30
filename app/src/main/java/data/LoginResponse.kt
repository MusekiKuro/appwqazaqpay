package com.example.qazaqpaybank.data

data class LoginResponse(
    val token: String? = null,
    val email: String? = null,
    val message: String? = null,
    val mfaRequired: Boolean = false
)
