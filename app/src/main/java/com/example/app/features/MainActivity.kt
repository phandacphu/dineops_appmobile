package com.example.app.features

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.app.R
import com.example.app.core.network.RetrofitClient
import com.example.app.data.model.OrderListResponse
import com.example.app.data.model.TableListResponse
import com.google.android.material.card.MaterialCardView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPref = getSharedPreferences("DineOpsPrefs", Context.MODE_PRIVATE)
        val userName = sharedPref.getString("USER_NAME", "Người dùng")
        val token = sharedPref.getString("ACCESS_TOKEN", "")

        val tvBrandName = findViewById<TextView>(R.id.tvBrandName)
        tvBrandName.text = "Xin chào, $userName"

        val tvOrderCount = findViewById<TextView>(R.id.tvOrderCount)
        val tvAvailableTableCount = findViewById<TextView>(R.id.tvAvailableTableCount)

        val btnLogout = findViewById<ImageView>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            with(sharedPref.edit()) {
                clear()
                apply()
            }
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        val cardOrderList = findViewById<MaterialCardView>(R.id.cardOrderList)
        cardOrderList.setOnClickListener {
            startActivity(Intent(this, OrderListActivity::class.java))
        }

        val bearerToken = "Bearer $token"

        RetrofitClient.instance.getOrders(bearerToken).enqueue(object : Callback<OrderListResponse> {
            override fun onResponse(call: Call<OrderListResponse>, response: Response<OrderListResponse>) {
                if (response.isSuccessful) {
                    val element = response.body()?.data
                    var count = 0
                    if (element != null) {
                        if (element.isJsonObject) {
                            count = element.asJsonObject.get("count")?.asInt ?: 0
                        } else if (element.isJsonArray) {
                            count = element.asJsonArray.size()
                        }
                    }
                    tvOrderCount.text = count.toString()
                } else {
                    tvOrderCount.text = "0"
                }
            }

            override fun onFailure(call: Call<OrderListResponse>, t: Throwable) {
                tvOrderCount.text = "0"
            }
        })

        RetrofitClient.instance.getAvailableTables(bearerToken).enqueue(object : Callback<TableListResponse> {
            override fun onResponse(call: Call<TableListResponse>, response: Response<TableListResponse>) {
                if (response.isSuccessful) {
                    val element = response.body()?.data
                    var count = 0
                    if (element != null) {
                        if (element.isJsonArray) {
                            count = element.asJsonArray.size()
                        } else if (element.isJsonObject) {
                            val results = element.asJsonObject.get("results")
                            if (results != null && results.isJsonArray) {
                                count = results.asJsonArray.size()
                            }
                        }
                    }
                    tvAvailableTableCount.text = count.toString()
                } else {
                    tvAvailableTableCount.text = "0"
                }
            }

            override fun onFailure(call: Call<TableListResponse>, t: Throwable) {
                tvAvailableTableCount.text = "0"
            }
        })
    }
}