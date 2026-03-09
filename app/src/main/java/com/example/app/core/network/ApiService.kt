package com.example.app.core.network

import com.example.app.data.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("health")
    suspend fun healthCheck(): String

    @POST("users/register/")
    fun register(@Body request: RegisterRequest): Call<AuthResponse>

    @POST("users/login/")
    fun login(@Body request: LoginRequest): Call<AuthResponse>

    @GET("orders/")
    fun getOrders(@Header("Authorization") token: String): Call<OrderListResponse>

    @GET("tables/available/")
    fun getAvailableTables(@Header("Authorization") token: String): Call<TableListResponse>

    // Thêm API lấy chi tiết đơn hàng
    @GET("orders/{id}/")
    fun getOrderDetail(
        @Path("id") orderId: Int,
        @Header("Authorization") token: String
    ): Call<OrderDetailResponse>
}