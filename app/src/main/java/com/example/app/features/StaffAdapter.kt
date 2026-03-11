package com.example.app.features

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R

class StaffAdapter(
    private var staffList: List<Staff>,
    private val onItemClick: (Staff, Int) -> Unit,
    private val onActionClick: (Staff) -> Unit
) : RecyclerView.Adapter<StaffAdapter.StaffViewHolder>() {

    // 1. ViewHolder: Khai báo các view
    class StaffViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtName: TextView = view.findViewById(R.id.txtStaffName)
        val txtRole: TextView = view.findViewById(R.id.txtStaffRole)
        val txtInfo: TextView = view.findViewById(R.id.txtStaffInfo)
        val txtArea: TextView = view.findViewById(R.id.txtStaffArea)
        val btnAction: Button = view.findViewById(R.id.btnAction)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_staff, parent, false)
        return StaffViewHolder(view)
    }

    // 2. Gắn dữ liệu
    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
        val staff = staffList[position]

        holder.txtName.text = staff.name
        holder.txtRole.text = "Vai trò: ${staff.role}"
        holder.txtInfo.text = staff.info

        // Hiển thị khu vực
        holder.txtArea.text = "Khu vực: ${staff.area}"

        // Logic đổi chữ nút bấm
        holder.btnAction.text = if (staff.status == "Nghỉ") "GỌI LÀM" else "PHÂN CÔNG"

        // Xử lý click
        holder.itemView.setOnClickListener {
            onItemClick(staff, position)
        }

        holder.btnAction.setOnClickListener {
            onActionClick(staff)
        }
    }

    override fun getItemCount(): Int = staffList.size

    // Tối ưu hàm update
    fun updateData(newList: ArrayList<Staff>) {
        this.staffList = newList
        notifyDataSetChanged()
    }
}