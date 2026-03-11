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

class EditIngredientActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_ingredient)

        // 1. Ánh xạ các View từ giao diện XML
        val edtName = findViewById<EditText>(R.id.edtEditName)
        val edtQty = findViewById<EditText>(R.id.edtEditQuantity)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        val btnDelete = findViewById<Button>(R.id.btnDelete)
        val btnBack = findViewById<ImageView>(R.id.btnBack)

        // 2. Nhận dữ liệu cũ từ WarehouseActivity gửi sang
        val oldName = intent.getStringExtra("EXTRA_NAME") ?: ""
        val oldQty = intent.getStringExtra("EXTRA_QTY") ?: ""
        val position = intent.getIntExtra("EXTRA_POS", -1)

        // Hiển thị dữ liệu cũ lên các ô nhập để Phú sửa
        edtName.setText(oldName)
        edtQty.setText(oldQty)

        // 3. Xử lý nút LƯU THAY ĐỔI (Cập nhật)
        btnUpdate.setOnClickListener {
            val newName = edtName.text.toString()
            val newQty = edtQty.text.toString()

            if (newName.isNotEmpty() && newQty.isNotEmpty()) {
                val resultIntent = Intent()
                resultIntent.putExtra("ACTION", "UPDATE") // Gửi lệnh sửa
                resultIntent.putExtra("EXTRA_NAME", newName)
                resultIntent.putExtra("EXTRA_QTY", newQty)
                resultIntent.putExtra("EXTRA_POS", position)

                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Vui lòng không để trống!", Toast.LENGTH_SHORT).show()
            }
        }

        // 4. Xử lý nút XÓA NGUYÊN LIỆU
        // 4. Xử lý nút XÓA NGUYÊN LIỆU (Có hỏi lại cho chắc)
        btnDelete.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Xác nhận xóa")
            builder.setMessage("Bạn có chắc chắn muốn xóa nguyên liệu này không?")

            // Nếu chọn CÓ
            builder.setPositiveButton("XÓA") { _, _ ->
                val resultIntent = Intent()
                resultIntent.putExtra("ACTION", "DELETE")
                resultIntent.putExtra("EXTRA_POS", position)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }

            // Nếu chọn HỦY
            builder.setNegativeButton("HỦY") { dialog, _ ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }

        // 5. Nút quay lại
        btnBack.setOnClickListener {
            finish()
        }
    }
}