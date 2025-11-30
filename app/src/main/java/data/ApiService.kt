package com.example.qazaqpaybank.data

import retrofit2.Response
import retrofit2.http.*

data class MfaRequest(
    val email: String,
    val code: String
)

data class Card(
    val id: Long,
    val cardNumber: String,
    val expiryDate: String,
    val cardHolder: String,
    val status: String,
    val limit: Double,
    val accountNumber: String
)

data class Account(
    val id: Long,
    val accountNumber: String,
    val balance: Double,
    val accountType: String,
    val currency: String,
    val active: Boolean
)

interface ApiService {
    @POST("/api/auth/login")
    suspend fun login(@Body req: LoginRequest): Response<LoginResponse>

    @POST("/api/auth/verify-mfa")
    suspend fun verifyMfa(@Body req: MfaRequest): Response<LoginResponse>


    @POST("/api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<LoginResponse>


    @GET("/api/cards/my-cards")
    suspend fun myCards(@Header("Authorization") token: String): List<Card>

    @GET("/api/account/my-accounts")
    suspend fun myAccounts(@Header("Authorization") token: String): List<Account>
}

