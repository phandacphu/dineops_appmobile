package com.example.app.features

import android.content.Context
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
import org.json.JSONObject
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

        btnLogin.setOnClickListener {
            val identifier = edtIdentifier.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (identifier.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tài khoản và mật khẩu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val loginRequest = LoginRequest(identifier, password)

            RetrofitClient.instance.login(loginRequest).enqueue(object : Callback<AuthResponse> {
                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                    if (response.isSuccessful) {
                        val authResponse = response.body()

                        if (authResponse?.code == 200 || authResponse?.status == "success") {
                            val authData = authResponse.data
                            val user = authData?.user

                            val sharedPref = getSharedPreferences("DineOpsPrefs", Context.MODE_PRIVATE)
                            with (sharedPref.edit()) {
                                putString("ACCESS_TOKEN", authData?.access_token)
                                putString("REFRESH_TOKEN", authData?.refresh_token)
                                user?.id?.let { putInt("USER_ID", it) }
                                putString("USER_NAME", user?.full_name)
                                apply()
                            }

                            startActivity(Intent(this@LoginActivity, MainActivityAll::class.java))
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, "Lỗi: ${authResponse?.msg}", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        try {
                            val errorBody = response.errorBody()?.string()
                            val jsonObject = JSONObject(errorBody ?: "")
                            val msg = jsonObject.getString("msg")
                            Toast.makeText(this@LoginActivity, msg, Toast.LENGTH_LONG).show()
                        } catch (e: Exception) {
                            Toast.makeText(this@LoginActivity, "Đăng nhập thất bại. Sai thông tin!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "   ", Toast.LENGTH_LONG).show()
                }
            })


            startActivity(Intent(this@LoginActivity, MainActivityAll::class.java))
            finish()
        }

        tvGoToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}