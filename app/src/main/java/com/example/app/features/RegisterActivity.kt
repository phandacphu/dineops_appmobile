package com.example.app.features

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.app.R
import com.example.app.core.network.RetrofitClient
import com.example.app.data.model.AuthResponse
import com.example.app.data.model.RegisterRequest
import org.json.JSONObject
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

            if (user.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập Username và Mật khẩu!", Toast.LENGTH_SHORT).show()
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

            RetrofitClient.instance.register(registerRequest).enqueue(object : Callback<AuthResponse> {
                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                    if (response.isSuccessful) {
                        // Chỉ cần server trả về mã 200-299 là ta coi như thành công
                        Toast.makeText(this@RegisterActivity, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        // Nếu server trả về lỗi (mã 4xx, 5xx)
                        Toast.makeText(this@RegisterActivity, "Lỗi: Không đăng ký được!", Toast.LENGTH_SHORT).show()

                        // MẸO: In lỗi ra Logcat để bạn biết vì sao không đăng ký được (ví dụ: trùng email)
                        android.util.Log.e("DEBUG_LOI", "Nội dung lỗi: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, "Lỗi kết nối Server", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}