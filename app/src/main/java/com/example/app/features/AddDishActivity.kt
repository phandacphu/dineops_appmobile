package com.example.app.features

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class AddDishActivity : AppCompatActivity() {

    private lateinit var edtName: TextInputEditText
    private lateinit var edtPrice: TextInputEditText
    private lateinit var btnSave: Button
    private lateinit var imgDishSelect: ImageView
    private lateinit var fabSelectImage: FloatingActionButton
    private lateinit var btnBack: ImageView

    private var selectedImageUri: Uri? = null
    private val PICK_IMAGE_REQUEST = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_dish)

        // Ánh xạ các view
        edtName = findViewById(R.id.edtName)
        edtPrice = findViewById(R.id.edtPrice)
        btnSave = findViewById(R.id.btnSave)
        imgDishSelect = findViewById(R.id.imgDishSelect)
        fabSelectImage = findViewById(R.id.fabSelectImage)
        btnBack = findViewById(R.id.btnBack)

        // Nút chọn ảnh
        fabSelectImage.setOnClickListener {
            openGallery()
        }

        // Nút quay lại
        btnBack.setOnClickListener { finish() }

        // Nút Lưu món ăn
        btnSave.setOnClickListener {
            val name = edtName.text.toString().trim()
            val priceStr = edtPrice.text.toString().trim()

            if (name.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val price = priceStr.toDoubleOrNull() ?: 0.0
            val intent = Intent()
            intent.putExtra("name", name)
            intent.putExtra("price", price)
            // Gửi đường dẫn ảnh (dưới dạng chuỗi) về MenuActivity
            intent.putExtra("imageUrl", selectedImageUri?.toString() ?: "")

            setResult(Activity.RESULT_OK, intent)
            Toast.makeText(this, "Đã thêm món ăn mới", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    // Hàm mở bộ sưu tập ảnh
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    // Nhận kết quả chọn ảnh
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            imgDishSelect.setImageURI(selectedImageUri)
            imgDishSelect.scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }
}
