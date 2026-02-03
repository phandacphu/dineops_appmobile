package com.example.app.features.order

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.data.model.Order

class OrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val rvOrders = findViewById<RecyclerView>(R.id.rvOrders)

        rvOrders.layoutManager = LinearLayoutManager(this)

        val fakeList = listOf(
            Order("#B-1", "ĐANG CHẾ BIẾN", "450.000đ"),
            Order("#B-2", "ĐANG CHỜ", "220.000đ"),
            Order("#B-3", "HOÀN THÀNH", "780.000đ")
        )

        rvOrders.adapter = OrderAdapter(fakeList)
    }
}
