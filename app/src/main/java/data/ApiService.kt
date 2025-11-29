package com.example.qazaqpaybank.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

@JsonClass(generateAdapter = true)
data class LoginRequest(
    val email: String,
    val password: String
)

@JsonClass(generateAdapter = true)
data class MfaRequest(
    val email: String,
    val code: String
)

@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "token") val token: String? = null,
    @Json(name = "mfaRequired") val mfaRequired: Boolean = false,
    @Json(name = "message") val message: String = ""
)


@JsonClass(generateAdapter = true)
data class Card(
    val id: Long,
    val cardNumber: String,
    val expiryDate: String,
    val cardHolder: String,
    val status: String,
    val limit: Double,
    val accountNumber: String
)

@JsonClass(generateAdapter = true)
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

    @GET("/api/cards/my-cards")
    suspend fun myCards(@Header("Authorization") token: String): List<Card>

    @GET("/api/account/my-accounts")
    suspend fun myAccounts(@Header("Authorization") token: String): List<Account>
}

object ApiClient {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
