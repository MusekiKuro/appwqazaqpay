package com.example.qazaqpaybank.data

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val password: String,
    val iin: String
)
