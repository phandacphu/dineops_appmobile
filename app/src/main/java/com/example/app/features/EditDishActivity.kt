package com.example.app.features

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app.R

class EditDishActivity : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtPrice: EditText
    private lateinit var btnUpdate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_dish)

        edtName = findViewById(R.id.edtName)
        edtPrice = findViewById(R.id.edtPrice)
        btnUpdate = findViewById(R.id.btnUpdate)

        // Nhận dữ liệu cũ để hiển thị
        val id = intent.getIntExtra("id", -1)
        edtName.setText(intent.getStringExtra("name"))
        edtPrice.setText(intent.getDoubleExtra("price", 0.0).toString())

        btnUpdate.setOnClickListener {
            val updatedName = edtName.text.toString()
            val updatedPrice = edtPrice.text.toString().toDoubleOrNull() ?: 0.0

            if (updatedName.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên món", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Gửi dữ liệu đã sửa về MenuActivity
            val resultIntent = Intent()
            resultIntent.putExtra("id", id)
            resultIntent.putExtra("name", updatedName)
            resultIntent.putExtra("price", updatedPrice)
            
            setResult(RESULT_OK, resultIntent)
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
