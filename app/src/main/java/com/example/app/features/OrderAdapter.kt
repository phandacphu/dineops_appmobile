package com.example.app.features

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.data.model.OrderItem

class OrderAdapter(
    private var orders: List<OrderItem>,
    private val onItemClick: (OrderItem) -> Unit
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    fun updateData(newOrders: List<OrderItem>) {
        orders = newOrders
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.bind(order)

        holder.itemView.setOnClickListener {
            onItemClick(order)
        }
    }

    override fun getItemCount(): Int = orders.size

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvOrderNumber: TextView = itemView.findViewById(R.id.tvOrderNumber)
        private val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        private val tvTotalAmount: TextView = itemView.findViewById(R.id.tvTotalAmount)
        private val tvTableInfo: TextView = itemView.findViewById(R.id.tvTableInfo)
        private val tvItemsCount: TextView = itemView.findViewById(R.id.tvItemsCount)

        fun bind(order: OrderItem) {
            tvOrderNumber.text = "#ĐH-${order.id}"
            tvStatus.text = order.status_display?.uppercase() ?: "KHÔNG RÕ"

            val amount = order.total_amount?.replace(".00", "") ?: "0"
            tvTotalAmount.text = "${amount}đ"

            tvTableInfo.text = "Bàn ${order.table_number} • ${order.user_name ?: "Khách"}"
            tvItemsCount.text = "${order.items_count ?: 0} món"

            when (order.status) {
                "pending" -> {
                    tvStatus.setTextColor(Color.parseColor("#E65100"))
                    tvStatus.setBackgroundColor(Color.parseColor("#FFE0B2"))
                }
                "confirmed", "served" -> {
                    tvStatus.setTextColor(Color.parseColor("#1565C0"))
                    tvStatus.setBackgroundColor(Color.parseColor("#E3F2FD"))
                }
                "completed" -> {
                    tvStatus.setTextColor(Color.parseColor("#2E7D32"))
                    tvStatus.setBackgroundColor(Color.parseColor("#C8E6C9"))
                }
                else -> {
                    tvStatus.setTextColor(Color.parseColor("#757575"))
                    tvStatus.setBackgroundColor(Color.parseColor("#E0E0E0"))
                }
            }
        }
    }
}