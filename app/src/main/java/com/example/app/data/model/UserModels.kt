package com.example.app.data.model

data class LoginRequest(
    val identifier: String,
    val password: String
)

data class RegisterRequest(
    val email: String?,
    val user_name: String?,
    val phone_number: String?,
    val password: String,
    val password_confirm: String,
    val first_name: String,
    val last_name: String
)

data class AuthResponse(
    val status: String?,
    val code: Int?,
    val msg: String?,
    val data: AuthData?
)

data class AuthData(
    val access_token: String?,
    val refresh_token: String?,
    val token_type: String?,
    val expires_in: Int?,
    val user: UserInfo?
)

data class UserInfo(
    val id: Int?,
    val uuid: String?,
    val user_name: String?,
    val email: String?,
    val full_name: String?
)