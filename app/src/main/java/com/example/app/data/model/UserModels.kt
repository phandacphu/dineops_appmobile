package com.example.app.data.model // Đảm bảo dòng này khớp với cấu trúc thư mục của bạn

// 1. Thùng chứa cho Đăng nhập (Request)
data class LoginRequest(
    val identifier: String,
    val password: String
)

// 2. Thùng chứa cho Đăng ký (Request)
data class RegisterRequest(
    val email: String?,
    val user_name: String?,
    val phone_number: String?,
    val password: String,
    val password_confirm: String,
    val first_name: String,
    val last_name: String
)

// 3. Phản hồi chung từ API (Response)
data class AuthResponse(
    val status: String,
    val code: Int,
    val msg: String,
    val data: AuthData?
)

// 4. Chi tiết dữ liệu bên trong (Token và User)
data class AuthData(
    val access_token: String,
    val refresh_token: String,
    val token_type: String,
    val expires_in: Int,
    val user: UserInfo
)

// 5. Thông tin chi tiết của User nhận về
data class UserInfo(
    val id: Int,
    val uuid: String,
    val user_name: String,
    val email: String?,
    val full_name: String
)