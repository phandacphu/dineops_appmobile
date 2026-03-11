package com.example.app.features

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.data.model.Dish
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DishActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DishAdapter
    private lateinit var searchView: SearchView
    private lateinit var fabAdd: FloatingActionButton

    private var dishes = mutableListOf<Dish>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        recyclerView = findViewById(R.id.rvMenu)
        searchView = findViewById(R.id.edtSearch)
        fabAdd = findViewById(R.id.btnAddDish)

        recyclerView.layoutManager = LinearLayoutManager(this)

        // dữ liệu mẫu
        dishes.add(Dish(1, "Phở bò", 40000.0, "Food", ""))
        dishes.add(Dish(2, "Bún bò", 35000.0, "Food", ""))

        adapter = DishAdapter(this, dishes)

        recyclerView.adapter = adapter

        fabAdd.setOnClickListener {

            val intent = Intent(this, AddDishActivity::class.java)
            startActivity(intent)
        }

        // tìm kiếm
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                val filtered = dishes.filter {
                    it.name.lowercase().contains(newText!!.lowercase())
                }

                adapter.updateList(filtered)

                return true
            }
        })
    }
}