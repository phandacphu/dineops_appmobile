package com.example.app.features

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.core.network.RetrofitClient
import com.example.app.data.model.OrderData
import com.example.app.data.model.OrderItem
import com.example.app.data.model.OrderListResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderListActivity : AppCompatActivity() {

    private lateinit var rvOrders: RecyclerView
    private lateinit var orderAdapter: OrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener { finish() }

        rvOrders = findViewById(R.id.rvOrders)
        rvOrders.layoutManager = LinearLayoutManager(this)

        // Chuyển sang màn hình chi tiết khi click
        orderAdapter = OrderAdapter(emptyList()) { selectedOrder ->
            selectedOrder.id?.let { orderId ->
                val intent = Intent(this, OrderDetailActivity::class.java)
                intent.putExtra("ORDER_ID", orderId)
                startActivity(intent)
            }
        }
        rvOrders.adapter = orderAdapter

        fetchOrders()
    }

    private fun fetchOrders() {
        val sharedPref = getSharedPreferences("DineOpsPrefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("ACCESS_TOKEN", "")
        val bearerToken = "Bearer $token"

        RetrofitClient.instance.getOrders(bearerToken).enqueue(object : Callback<OrderListResponse> {
            override fun onResponse(call: Call<OrderListResponse>, response: Response<OrderListResponse>) {
                if (response.isSuccessful) {
                    val element = response.body()?.data
                    val orders = mutableListOf<OrderItem>()

                    if (element != null) {
                        try {
                            val gson = Gson()
                            if (element.isJsonArray) {
                                val listType = object : TypeToken<List<OrderItem>>() {}.type
                                val list: List<OrderItem> = gson.fromJson(element, listType)
                                orders.addAll(list)
                            } else if (element.isJsonObject) {
                                val orderData = gson.fromJson(element, OrderData::class.java)
                                orderData.results?.let { orders.addAll(it) }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    if (orders.isEmpty()) {
                        Toast.makeText(this@OrderListActivity, "Chưa có đơn hàng nào!", Toast.LENGTH_SHORT).show()
                    }

                    orderAdapter.updateData(orders)
                } else {
                    Toast.makeText(this@OrderListActivity, "Lỗi ${response.code()}: Server từ chối", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<OrderListResponse>, t: Throwable) {
                Toast.makeText(this@OrderListActivity, "Lỗi kết nối: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}