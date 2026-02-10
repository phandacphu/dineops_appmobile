package com.example.app.features

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.app.R

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // 1. Ánh xạ các View từ XML
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val tvFooterLogin = findViewById<TextView>(R.id.tvFooterLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        val etUser = findViewById<EditText>(R.id.etUser)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPass = findViewById<EditText>(R.id.etPass)
        val etConfirm = findViewById<EditText>(R.id.etConfirm)
        val cbTerms = findViewById<CheckBox>(R.id.cbTerms)

        // 2. Chức năng quay lại trang Đăng nhập
        btnBack.setOnClickListener { finish() }
        tvFooterLogin.setOnClickListener { finish() }

        // 3. Xử lý khi bấm nút Đăng ký
        btnRegister.setOnClickListener {
            val user = etUser.text.toString().trim()
            val pass = etPass.text.toString().trim()
            val confirm = etConfirm.text.toString().trim()

            // Kiểm tra các trường hợp lỗi thường gặp
            if (user.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass != confirm) {
                Toast.makeText(this, "Mật khẩu xác nhận không khớp!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!cbTerms.isChecked) {
                Toast.makeText(this, "Bạn cần đồng ý với điều khoản sử dụng!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Nếu không có lỗi, thông báo thành công và quay về Login
            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}