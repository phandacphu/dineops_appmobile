package com.example.app.features

<<<<<<< HEAD
import android.content.Context
=======
>>>>>>> e60e341f603258cc3534cac0c10d2dcf96724631
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
<<<<<<< HEAD
import org.json.JSONObject
=======
>>>>>>> e60e341f603258cc3534cac0c10d2dcf96724631
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

<<<<<<< HEAD
=======
        // 2. Xử lý khi nhấn nút Đăng nhập
>>>>>>> e60e341f603258cc3534cac0c10d2dcf96724631
        btnLogin.setOnClickListener {
            val identifier = edtIdentifier.text.toString().trim()
            val password = edtPassword.text.toString().trim()

<<<<<<< HEAD
=======
            // Kiểm tra nhập liệu cơ bản
>>>>>>> e60e341f603258cc3534cac0c10d2dcf96724631
            if (identifier.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tài khoản và mật khẩu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

<<<<<<< HEAD
            // ================================
            // PHẦN GỌI API LOGIN (ĐÃ COMMENT)
            // Nếu sau này cần dùng lại API
            // chỉ cần bỏ dấu // phía dưới
            // ================================

            /*
            val loginRequest = LoginRequest(identifier, password)

=======
            // Tạo request gửi lên Server
            val loginRequest = LoginRequest(identifier, password)

            // Gọi API thật qua Retrofit
>>>>>>> e60e341f603258cc3534cac0c10d2dcf96724631
            RetrofitClient.instance.login(loginRequest).enqueue(object : Callback<AuthResponse> {
                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                    if (response.isSuccessful) {
                        val authResponse = response.body()

<<<<<<< HEAD
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
=======
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
>>>>>>> e60e341f603258cc3534cac0c10d2dcf96724631
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
<<<<<<< HEAD
                    Toast.makeText(this@LoginActivity, "Lỗi kết nối", Toast.LENGTH_LONG).show()
                }
            })
            */

            startActivity(Intent(this@LoginActivity, MainActivityAll::class.java))
            finish()
        }

        tvGoToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
=======
                    t.printStackTrace()
                    Toast.makeText(this@LoginActivity, "Lỗi kết nối: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }

        // 3. Chuyển sang màn hình Đăng ký
        tvGoToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
>>>>>>> e60e341f603258cc3534cac0c10d2dcf96724631
        }
    }
}