package com.example.app.data.model

import com.google.gson.JsonElement

data class OrderListResponse(
    val status: String?,
    val code: Int?,
    val msg: String?,
    val data: JsonElement?
)

data class OrderData(
    val count: Int?,
    val results: List<OrderItem>?
)

data class OrderItem(
    val id: Int?,
    val table_number: String?,
    val user_name: String?,
    val status: String?,
    val status_display: String?,
    val total_amount: String?,
    val items_count: Int?,
    val created_at: String?
)

data class TableListResponse(
    val status: String?,
    val code: Int?,
    val msg: String?,
    val data: JsonElement?
)

data class TableItem(
    val id: Int?,
    val table_number: String?,
    val status: String?
)

// ========= MODELS CHO CHI TIẾT ĐƠN HÀNG =========
data class OrderDetailResponse(
    val status: String?,
    val code: Int?,
    val msg: String?,
    val data: OrderDetailData?
)

data class OrderDetailData(
    val id: Int?,
    val table_number: String?,
    val user_name: String?,
    val status: String?,
    val status_display: String?,
    val total_amount: String?,
    val created_at: String?,
    val items: List<OrderDetailItem>?
)

data class OrderDetailItem(
    val id: Int?,
    val product_name: String?,
    val size: String?,
    val quantity: Int?,
    val price: String?,
    val total_price: String?,
    val notes: String?
)