package com.example.app.features

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class WarehouseActivity : AppCompatActivity() {

    private lateinit var adapter: IngredientAdapter
    private lateinit var fullList: List<Ingredient>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_warehouse)

        // 1. Khởi tạo dữ liệu mẫu giống hệt mẫu ảnh 1 của Phú
        fullList = listOf(
            Ingredient("Ức gà", "Đạm", "4.2 kg", "SẮP HẾT"),
            Ingredient("Sữa tươi", "Sữa & Bơ", "24.0 L", "TỐT"),
            Ingredient("Cà chua", "Rau củ", "12.5 kg", "TỐT"),
            Ingredient("Bơ lạt", "Sữa & Bơ", "0.8 kg", "SẮP HẾT"),
            Ingredient("Trứng gà", "Đạm", "50 quả", "TỐT"),
            Ingredient("Hành tây", "Rau củ", "2.0 kg", "SẮP HẾT")
        )

        // 2. Thiết lập RecyclerView
        val rvIngredients = findViewById<RecyclerView>(R.id.rvIngredients)
        adapter = IngredientAdapter(fullList)
        rvIngredients.layoutManager = LinearLayoutManager(this)
        rvIngredients.adapter = adapter

        // 3. Xử lý chức năng TÌM KIẾM
        val edtSearch = findViewById<EditText>(R.id.edtSearch) // Đảm bảo ID này trùng với ID ô tìm kiếm trong XML
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Lọc danh sách dựa trên nội dung Phú gõ
                filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // 4. Xử lý nút NHẬP KHO
        val btnImport = findViewById<FloatingActionButton>(R.id.btnImport) // Hoặc Button tùy ID Phú đặt
        btnImport.setOnClickListener {
            Toast.makeText(this, "Mở màn hình Thêm nguyên liệu mới", Toast.LENGTH_SHORT).show()
            // Sau này Phú có thể dùng Intent để mở một Activity nhập liệu tại đây
        }

        // 5. Nút quay lại (Dấu mũi tên ở Toolbar)
        findViewById<android.view.View>(R.id.btnBack)?.setOnClickListener {
            finish()
        }
    }

    // Hàm lọc dữ liệu
    private fun filter(text: String) {
        val filteredList = fullList.filter {
            it.name.contains(text, ignoreCase = true)
        }
        adapter.updateData(filteredList)
    }
}