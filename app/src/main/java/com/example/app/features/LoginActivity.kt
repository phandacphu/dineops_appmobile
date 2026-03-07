package com.example.app.features

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app.R
import com.example.app.core.network.RetrofitClient
import com.example.app.data.model.AuthResponse
import com.example.app.data.model.LoginRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val edtIdentifier = findViewById<EditText>(R.id.etUser)
        val edtPassword = findViewById<EditText>(R.id.etPass)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvGoToRegister = findViewById<TextView>(R.id.tvGoToRegister)

        // 2. Xử lý khi nhấn nút Đăng nhập
        btnLogin.setOnClickListener {
            val identifier = edtIdentifier.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            // Kiểm tra nhập liệu cơ bản
            if (identifier.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tài khoản và mật khẩu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Tạo request gửi lên Server
            val loginRequest = LoginRequest(identifier, password)

            // Gọi API thật qua Retrofit
            RetrofitClient.instance.login(loginRequest).enqueue(object : Callback<AuthResponse> {
                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                    if (response.isSuccessful) {
                        val authResponse = response.body()

                        if (authResponse?.status == "success") {
                            val user = authResponse.data?.user
                            Toast.makeText(this@LoginActivity, "Chào mừng ${user?.full_name}!", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this@LoginActivity, MainActivityAll::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, "Server: ${authResponse?.msg}", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        val error = response.errorBody()?.string()
                        Toast.makeText(this@LoginActivity, "API Error: $error", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(this@LoginActivity, "Lỗi kết nối: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }

        // 3. Chuyển sang màn hình Đăng ký
        tvGoToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}