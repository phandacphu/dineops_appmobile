package com.example.app.features

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R

// 1. Cấu trúc dữ liệu giữ nguyên
data class Ingredient(
    val name: String,
    val category: String,
    val quantity: String,
    val status: String
)

// 2. Thêm tham số onItemClick vào Constructor của Adapter
class IngredientAdapter(
    private var items: List<Ingredient>,
    private val onItemClick: (Ingredient, Int) -> Unit // Hàm xử lý khi click: trả về món hàng và vị trí
) : RecyclerView.Adapter<IngredientAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtCategory: TextView = view.findViewById(R.id.txtCategory)
        val txtQuantity: TextView = view.findViewById(R.id.txtQuantity)
        val txtStatusTag: TextView = view.findViewById(R.id.txtStatusTag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ingredient, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.txtName.text = item.name
        holder.txtCategory.text = "Danh mục: ${item.category}"
        holder.txtQuantity.text = item.quantity
        holder.txtStatusTag.text = item.status

        if (item.status == "SẮP HẾT") {
            holder.txtStatusTag.visibility = View.VISIBLE
        } else {
            holder.txtStatusTag.visibility = View.GONE
        }

        // --- PHẦN SỬA ĐỔI CHÍNH: Lắng nghe sự kiện click trên toàn bộ dòng ---
        holder.itemView.setOnClickListener {
            onItemClick(item, position)
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newList: List<Ingredient>) {
        this.items = newList
        notifyDataSetChanged()
    }
}