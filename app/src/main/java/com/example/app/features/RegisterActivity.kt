package com.example.app.features

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.app.R
import com.example.app.core.network.RetrofitClient
import com.example.app.data.model.AuthResponse
import com.example.app.data.model.RegisterRequest
<<<<<<< HEAD
import org.json.JSONObject
=======
>>>>>>> e60e341f603258cc3534cac0c10d2dcf96724631
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
<<<<<<< HEAD
=======

>>>>>>> e60e341f603258cc3534cac0c10d2dcf96724631
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

<<<<<<< HEAD
            if (user.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập Username và Mật khẩu!", Toast.LENGTH_SHORT).show()
=======
            // Kiểm tra nhập liệu cơ bản
            if (user.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
>>>>>>> e60e341f603258cc3534cac0c10d2dcf96724631
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

<<<<<<< HEAD
            // Xử lý tách Tên để thỏa mãn yêu cầu của API
            val nameParts = user.split(" ")
            val firstName = nameParts.lastOrNull() ?: user
            val lastName = if (nameParts.size > 1) nameParts.dropLast(1).joinToString(" ") else ""

            val registerRequest = RegisterRequest(
                email = email.ifEmpty { null },
                user_name = user.replace(" ", "").lowercase(),
                phone_number = phone.ifEmpty { null },
                password = pass,
                password_confirm = confirm,
                first_name = firstName,
                last_name = lastName
            )

=======
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
>>>>>>> e60e341f603258cc3534cac0c10d2dcf96724631
            RetrofitClient.instance.register(registerRequest).enqueue(object : Callback<AuthResponse> {
                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                    if (response.isSuccessful) {
<<<<<<< HEAD
                        // Chỉ cần server trả về mã 200-299 là ta coi như thành công
                        Toast.makeText(this@RegisterActivity, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        // Nếu server trả về lỗi (mã 4xx, 5xx)
                        Toast.makeText(this@RegisterActivity, "Lỗi: Không đăng ký được!", Toast.LENGTH_SHORT).show()

                        // MẸO: In lỗi ra Logcat để bạn biết vì sao không đăng ký được (ví dụ: trùng email)
                        android.util.Log.e("DEBUG_LOI", "Nội dung lỗi: ${response.errorBody()?.string()}")
=======
                        val authResponse = response.body()
                        if (authResponse?.status == "success") {
<<<<<<< HEAD
                            Toast.makeText(this@RegisterActivity, "Đăng ký thành công! Đang chuyển hướng...", Toast.LENGTH_LONG).show()
                            finish()
                        } else {
                            Toast.makeText(this@RegisterActivity, "Lỗi: ${authResponse?.msg}", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Bắt lỗi 400 từ API (vd: "Email already exists")
                        try {
                            val errorBody = response.errorBody()?.string()
                            val jsonObject = JSONObject(errorBody ?: "")
                            val msg = jsonObject.getString("msg")
                            Toast.makeText(this@RegisterActivity, msg, Toast.LENGTH_LONG).show()
                        } catch (e: Exception) {
                            Toast.makeText(this@RegisterActivity, "Đăng ký thất bại. Tài khoản có thể đã tồn tại!", Toast.LENGTH_SHORT).show()
                        }
=======
                            // Thành công!
                            Toast.makeText(this@RegisterActivity, "Đăng ký thành công!", Toast.LENGTH_LONG).show()
                            finish()
                        } else {
                            // Lỗi từ Server trả về
                            Toast.makeText(this@RegisterActivity, "Lỗi: ${authResponse?.msg}", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@RegisterActivity, "Đăng ký thất bại. Tài khoản có thể đã tồn tại!", Toast.LENGTH_SHORT).show()
>>>>>>> e60e341f603258cc3534cac0c10d2dcf96724631
>>>>>>> 4d22209d08c3f5df9efc8aba2f9241003a457819
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
<<<<<<< HEAD
                    Toast.makeText(this@RegisterActivity, "Lỗi kết nối Server", Toast.LENGTH_LONG).show()
=======
                    // Lỗi kết nối (Mất mạng, sai IP Server...)
                    Toast.makeText(this@RegisterActivity, "Lỗi kết nối: ${t.message}", Toast.LENGTH_LONG).show()
>>>>>>> e60e341f603258cc3534cac0c10d2dcf96724631
                }
            })
        }
    }
}