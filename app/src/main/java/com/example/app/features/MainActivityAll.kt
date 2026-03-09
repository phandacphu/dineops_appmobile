package com.example.app.features

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.app.R

class MainActivityAll : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_all)

        val btnWarehouse = findViewById<View>(R.id.cardWarehouse)

        // 2. Thiết lập sự kiện bấm nút
        btnWarehouse.setOnClickListener {
            val intent = Intent(this, WarehouseActivity::class.java)
            startActivity(intent)
        }
    }
}