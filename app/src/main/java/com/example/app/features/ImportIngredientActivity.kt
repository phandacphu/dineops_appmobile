package com.example.app.features

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app.R

class ImportIngredientActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_import_ingredient)

        // 1. Tìm các thành phần trên giao diện
        val edtName = findViewById<EditText>(R.id.edtName)
        val edtQuantity = findViewById<EditText>(R.id.edtQuantity)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnBack = findViewById<ImageView>(R.id.btnBack)

        // 2. Xử lý khi bấm nút XÁC NHẬN NHẬP KHO
        btnSave.setOnClickListener {
            val name = edtName.text.toString()
            val quantity = edtQuantity.text.toString()

            if (name.isEmpty() || quantity.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
            } else {
                // --- PHẦN SỬA ĐỔI CHÍNH Ở ĐÂY ---
                // Tạo một Intent mới để chứa dữ liệu trả về
                val resultIntent = Intent()
                resultIntent.putExtra("EXTRA_NAME", name)
                resultIntent.putExtra("EXTRA_QTY", quantity)

                // Đặt kết quả là OK và đính kèm dữ liệu
                setResult(Activity.RESULT_OK, resultIntent)

                // Hiển thị thông báo cho người dùng biết đã lưu
                Toast.makeText(this, "Đã gửi dữ liệu: $name", Toast.LENGTH_SHORT).show()

                // Đóng màn hình để quay về WarehouseActivity
                finish()
                // --------------------------------
            }
        }

        // 3. Xử lý nút quay lại
        btnBack?.setOnClickListener {
            setResult(Activity.RESULT_CANCELED) // Báo là người dùng hủy, không nhập gì
            finish()
        }
    }
}