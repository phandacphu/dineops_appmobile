package com.example.app.core.network

import retrofit2.http.GET
import com.example.app.data.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @GET("health")
    suspend fun healthCheck(): String

    // Đăng ký
    @POST("users/register/")
    fun register(@Body request: RegisterRequest): Call<AuthResponse>

    // Đăng nhập
    @POST("users/login/")
    fun login(@Body request: LoginRequest): Call<AuthResponse>
}
