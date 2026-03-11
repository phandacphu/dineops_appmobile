package com.example.app.features

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app.R
import com.example.app.data.model.Dish


class DishAdapter(
    private val context: Context,
    private var dishList: MutableList<Dish>
) : RecyclerView.Adapter<DishAdapter.DishViewHolder>() {

    class DishViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgDish: ImageView = view.findViewById(R.id.imgDish)
        val txtName: TextView = view.findViewById(R.id.txtDishName)
        val txtPrice: TextView = view.findViewById(R.id.txtDishPrice)
        val txtCategory: TextView = view.findViewById(R.id.txtDishCategory)
        val btnEdit: ImageButton = view.findViewById(R.id.btnEdit)
        val btnDelete: ImageButton = view.findViewById(R.id.btnDelete)
        val switchStatus: Switch = view.findViewById(R.id.switchStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dish, parent, false)
        return DishViewHolder(view)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        val dish = dishList[position]

        holder.txtName.text = dish.name
        holder.txtPrice.text = String.format("%,.0f VNĐ", dish.price)
        holder.txtCategory.text = dish.category

        Glide.with(context)
            .load(if (dish.imageUrl.isEmpty()) android.R.drawable.ic_menu_gallery else dish.imageUrl)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_report_image)
            .into(holder.imgDish)

        holder.btnDelete.setOnClickListener {
            val currentPos = holder.adapterPosition
            if (currentPos != RecyclerView.NO_POSITION) {
                dishList.removeAt(currentPos)
                notifyItemRemoved(currentPos)
                notifyItemRangeChanged(currentPos, dishList.size)
            }
        }

        // SỬA TẠI ĐÂY: Gọi hàm openEditDish của MenuActivity thay vì startActivity trực tiếp
        holder.btnEdit.setOnClickListener {
            if (context is MenuActivity) {
                context.openEditDish(dish)
            }
        }

    }

    override fun getItemCount(): Int = dishList.size

    fun updateList(newList: List<Dish>) {
        dishList = newList.toMutableList()
        notifyDataSetChanged()
    }
}
