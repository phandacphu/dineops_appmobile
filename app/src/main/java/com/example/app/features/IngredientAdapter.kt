package com.example.app.features

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R

// 1. Định nghĩa cấu trúc dữ liệu cho Nguyên liệu
data class Ingredient(
    val name: String,
    val category: String,
    val quantity: String,
    val status: String
)

class IngredientAdapter(private var items: List<Ingredient>) :
    RecyclerView.Adapter<IngredientAdapter.ViewHolder>() {

    // Lớp giữ các thành phần giao diện của mỗi dòng
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtCategory: TextView = view.findViewById(R.id.txtCategory)
        val txtQuantity: TextView = view.findViewById(R.id.txtQuantity)
        val txtStatusTag: TextView = view.findViewById(R.id.txtStatusTag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Kết nối với file item_ingredient.xml Phú đã sửa ở Bước 1
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ingredient, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        // Gán dữ liệu vào các TextView
        holder.txtName.text = item.name
        holder.txtCategory.text = "Danh mục: ${item.category}"
        holder.txtQuantity.text = item.quantity
        holder.txtStatusTag.text = item.status

        // Logic hiển thị nhãn trạng thái: Chỉ hiện màu đỏ khi "SẮP HẾT"
        if (item.status == "SẮP HẾT") {
            holder.txtStatusTag.visibility = View.VISIBLE
        } else {
            // Nếu là "TỐT" hoặc trạng thái khác thì ẩn cái tag đỏ đi cho giống mẫu
            holder.txtStatusTag.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = items.size

    // Hàm bổ sung để phục vụ chức năng TÌM KIẾM sau này
    fun updateData(newList: List<Ingredient>) {
        this.items = newList
        notifyDataSetChanged()
    }
}