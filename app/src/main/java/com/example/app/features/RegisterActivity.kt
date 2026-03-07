package com.example.app.features

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.app.R
import com.example.app.core.network.RetrofitClient
import com.example.app.data.model.AuthResponse
import com.example.app.data.model.RegisterRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val tvFooterLogin = findViewById<TextView>(R.id.tvFooterLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        val etUser = findViewById<EditText>(R.id.etUser)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPass = findViewById<EditText>(R.id.etPass)
        val etConfirm = findViewById<EditText>(R.id.etConfirm)
        val cbTerms = findViewById<CheckBox>(R.id.cbTerms)

        btnBack.setOnClickListener { finish() }
        tvFooterLogin.setOnClickListener { finish() }

        btnRegister.setOnClickListener {
            val user = etUser.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val pass = etPass.text.toString().trim()
            val confirm = etConfirm.text.toString().trim()

            // Kiểm tra nhập liệu cơ bản
            if (user.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass != confirm) {
                Toast.makeText(this, "Mật khẩu xác nhận không khớp!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass.length < 8) {
                Toast.makeText(this, "Mật khẩu phải có ít nhất 8 ký tự!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!cbTerms.isChecked) {
                Toast.makeText(this, "Bạn cần đồng ý với điều khoản sử dụng!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Đóng gói dữ liệu để gửi lên API
            // Vì mẫu API của bạn yêu cầu first_name và last_name, tạm thời mình để trống hoặc lấy từ username
            val registerRequest = RegisterRequest(
                email = if (email.isEmpty()) null else email,
                user_name = user,
                phone_number = if (phone.isEmpty()) null else phone,
                password = pass,
                password_confirm = confirm,
                first_name = user,
                last_name = ""
            )

            // Gọi API qua Retrofit
            RetrofitClient.instance.register(registerRequest).enqueue(object : Callback<AuthResponse> {
                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                    if (response.isSuccessful) {
                        val authResponse = response.body()
                        if (authResponse?.status == "success") {
                            // Thành công!
                            Toast.makeText(this@RegisterActivity, "Đăng ký thành công!", Toast.LENGTH_LONG).show()
                            finish()
                        } else {
                            // Lỗi từ Server trả về
                            Toast.makeText(this@RegisterActivity, "Lỗi: ${authResponse?.msg}", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@RegisterActivity, "Đăng ký thất bại. Tài khoản có thể đã tồn tại!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    // Lỗi kết nối (Mất mạng, sai IP Server...)
                    Toast.makeText(this@RegisterActivity, "Lỗi kết nối: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}