package com.example.app.features

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.app.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvGoToRegister = findViewById<TextView>(R.id.tvGoToRegister)

        // Khi bấm Đăng nhập -> Mở trang chủ mới
        btnLogin.setOnClickListener {
            val intent = Intent(this, MainActivityAll::class.java)
            startActivity(intent)
            finish()
        }

        // Khi bấm dòng Đăng ký -> Mở trang đăng ký
        tvGoToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}