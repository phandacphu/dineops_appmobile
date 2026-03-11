package com.example.app.features

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.app.R

class AddStaffActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_staff)

        val etName = findViewById<EditText>(R.id.etName)
        val etRole = findViewById<EditText>(R.id.etRole)
        val btnSave = findViewById<Button>(R.id.btnSaveStaff)

        btnSave.setOnClickListener {
            val name = etName.text.toString()
            val role = etRole.text.toString()

            // Gửi dữ liệu ngược lại StaffActivity
            val resultIntent = Intent()
            resultIntent.putExtra("NEW_NAME", name)
            resultIntent.putExtra("NEW_ROLE", role)
            setResult(RESULT_OK, resultIntent)
            finish() // Đóng màn hình này
        }
    }
}