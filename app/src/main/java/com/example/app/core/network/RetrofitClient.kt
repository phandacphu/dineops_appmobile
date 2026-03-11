package com.example.app.core.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
<<<<<<< HEAD
    private const val BASE_URL = "https://untaut-wickedly-amina.ngrok-free.dev/api/v1/"
=======
    private const val BASE_URL = "http://10.0.2.2:8000/api/v1/"
>>>>>>> e60e341f603258cc3534cac0c10d2dcf96724631

    // 2. Tạo bộ máy Retrofit
    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}