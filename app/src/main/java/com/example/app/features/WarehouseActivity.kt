package com.example.app.features

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class WarehouseActivity : AppCompatActivity() {

    private lateinit var adapter: IngredientAdapter
    private var fullList = arrayListOf<Ingredient>()

    // 1. NÂNG CẤP bộ đón dữ liệu: Xử lý cả Thêm, Sửa, Xóa
    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data ?: return@registerForActivityResult

            // Lấy các lệnh gửi từ màn hình Sửa/Thêm về
            val action = data.getStringExtra("ACTION") // "UPDATE", "DELETE" hoặc null
            val pos = data.getIntExtra("EXTRA_POS", -1)
            val name = data.getStringExtra("EXTRA_NAME") ?: ""
            val qty = data.getStringExtra("EXTRA_QTY") ?: ""

            when (action) {
                "DELETE" -> {
                    if (pos != -1) {
                        fullList.removeAt(pos)
                        adapter.updateData(fullList)
                        Toast.makeText(this, "Đã xóa nguyên liệu", Toast.LENGTH_SHORT).show()
                    }
                }
                "UPDATE" -> {
                    if (pos != -1) {
                        // Cập nhật món cũ tại vị trí pos
                        fullList[pos] = Ingredient(name, "Cập nhật", qty, "TỐT")
                        adapter.updateData(fullList)
                        Toast.makeText(this, "Đã cập nhật $name", Toast.LENGTH_SHORT).show()
                    }
                }
                else -> {
                    // Mặc định là THÊM MỚI (khi action = null)
                    if (name.isNotEmpty()) {
                        val newItem = Ingredient(name, "Mới nhập", "$qty kg", "TỐT")
                        fullList.add(0, newItem)
                        adapter.updateData(fullList)
                        findViewById<RecyclerView>(R.id.rvIngredients).scrollToPosition(0)
                        Toast.makeText(this, "Đã nhập kho: $name", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_warehouse)

        // Khởi tạo dữ liệu mẫu
        if (fullList.isEmpty()) {
            fullList.addAll(listOf(
                Ingredient("Ức gà", "Đạm", "4.2 kg", "SẮP HẾT"),
                Ingredient("Sữa tươi", "Sữa & Bơ", "24.0 L", "TỐT"),
                Ingredient("Cà chua", "Rau củ", "12.5 kg", "TỐT"),
                Ingredient("Bơ lạt", "Sữa & Bơ", "0.8 kg", "SẮP HẾT"),
                Ingredient("Trứng gà", "Đạm", "50 quả", "TỐT"),
                Ingredient("Hành tây", "Rau củ", "2.0 kg", "SẮP HẾT")
            ))
        }

        val rvIngredients = findViewById<RecyclerView>(R.id.rvIngredients)
        adapter = IngredientAdapter(fullList) { ingredient, position ->
            // Mở màn hình Sửa và gửi kèm dữ liệu cũ + vị trí
            val intent = Intent(this, EditIngredientActivity::class.java)
            intent.putExtra("EXTRA_NAME", ingredient.name)
            intent.putExtra("EXTRA_QTY", ingredient.quantity)
            intent.putExtra("EXTRA_POS", position)
            startForResult.launch(intent)
        }

        rvIngredients.layoutManager = LinearLayoutManager(this)
        rvIngredients.adapter = adapter

        // Chức năng Tìm kiếm
        val edtSearch = findViewById<EditText>(R.id.edtSearch)
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filter(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Nút Nhập kho
        val btnImport = findViewById<ExtendedFloatingActionButton>(R.id.btnImport)
        btnImport.setOnClickListener {
            val intent = Intent(this, ImportIngredientActivity::class.java)
            startForResult.launch(intent)
        }

        findViewById<android.view.View>(R.id.btnBack)?.setOnClickListener {
            finish()
        }
    }

    private fun filter(text: String) {
        val filteredList = if (text.isEmpty()) {
            fullList
        } else {
            fullList.filter { it.name.contains(text, ignoreCase = true) }
        }
        adapter.updateData(ArrayList(filteredList))
    }
}