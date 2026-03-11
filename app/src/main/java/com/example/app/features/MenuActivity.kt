package com.example.app.features

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.data.model.Dish

class MenuActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var edtSearch: EditText
    private lateinit var btnAddDish: ImageButton
    private lateinit var btnBack: ImageView

    private lateinit var adapter: DishAdapter
    private var dishList = mutableListOf<Dish>()

    private val ADD_DISH_REQUEST = 1
    private val EDIT_DISH_REQUEST = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        recyclerView = findViewById(R.id.rvMenu)
        edtSearch = findViewById(R.id.edtSearch)
        btnAddDish = findViewById(R.id.btnAddDish)
        btnBack = findViewById(R.id.btnBack)

        recyclerView.layoutManager = LinearLayoutManager(this)

        if (dishList.isEmpty()) {
            dishList.add(Dish(1, "Phở bò", 40000.0, "Food", ""))
            dishList.add(Dish(2, "Bún bò", 35000.0, "Food", ""))
            dishList.add(Dish(3, "Cơm tấm", 30000.0, "Food", ""))
            dishList.add(Dish(4, "Trà sữa", 25000.0, "Drink", ""))
        }

        adapter = DishAdapter(this, dishList)
        recyclerView.adapter = adapter

        btnBack.setOnClickListener { finish() }

        btnAddDish.setOnClickListener {
            val intent = Intent(this, AddDishActivity::class.java)
            startActivityForResult(intent, ADD_DISH_REQUEST)
        }

        setupSearch()
    }

    fun openEditDish(dish: Dish) {
        val intent = Intent(this, EditDishActivity::class.java).apply {
            putExtra("id", dish.id)
            putExtra("name", dish.name)
            putExtra("price", dish.price)
        }
        startActivityForResult(intent, EDIT_DISH_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val name = data.getStringExtra("name")
            
            // Xử lý giá tiền (chấp nhận cả String và Double để tránh lỗi)
            val price = if (data.hasExtra("price")) {
                val p = data.getSerializableExtra("price")
                if (p is String) p.toDoubleOrNull() ?: 0.0 else data.getDoubleExtra("price", 0.0)
            } else 0.0

            when (requestCode) {
                ADD_DISH_REQUEST -> {
                    if (name != null) {
                        dishList.add(Dish(dishList.size + 1, name, price, "Food", ""))
                        adapter.notifyDataSetChanged()
                    }
                }
                EDIT_DISH_REQUEST -> {
                    val id = data.getIntExtra("id", -1)
                    val index = dishList.indexOfFirst { it.id == id }
                    if (index != -1 && name != null) {
                        dishList[index].name = name
                        dishList[index].price = price
                        adapter.notifyItemChanged(index)
                    }
                }
            }
        }
    }

    private fun setupSearch() {
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val filteredList = dishList.filter { it.name.lowercase().contains(s.toString().lowercase()) }
                adapter.updateList(filteredList)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
