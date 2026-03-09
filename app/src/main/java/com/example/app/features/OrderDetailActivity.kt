package com.example.app.features

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.core.network.RetrofitClient
import com.example.app.data.model.OrderDetailItem
import com.example.app.data.model.OrderDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class OrderDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        val orderId = intent.getIntExtra("ORDER_ID", -1)
        if (orderId == -1) {
            Toast.makeText(this, "Lỗi: Không tìm thấy đơn hàng", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }
        fetchOrderDetail(orderId)
    }

    private fun fetchOrderDetail(orderId: Int) {
        val sharedPref = getSharedPreferences("DineOpsPrefs", Context.MODE_PRIVATE)
        val token = "Bearer ${sharedPref.getString("ACCESS_TOKEN", "")}"

        RetrofitClient.instance.getOrderDetail(orderId, token).enqueue(object : Callback<OrderDetailResponse> {
            override fun onResponse(call: Call<OrderDetailResponse>, response: Response<OrderDetailResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    if (data != null) {
                        findViewById<TextView>(R.id.tvHeaderTitle).text = "Đơn hàng #${data.id}"
                        findViewById<TextView>(R.id.tvHeaderStatus).text = data.status_display?.uppercase()
                        findViewById<TextView>(R.id.tvTable).text = "Bàn ${data.table_number}"
                        findViewById<TextView>(R.id.tvCustomer).text = data.user_name ?: "Khách vãng lai"
                        findViewById<TextView>(R.id.tvTime).text = formatDate(data.created_at)

                        val amount = data.total_amount?.replace(".00", "") ?: "0"
                        findViewById<TextView>(R.id.tvTotal).text = "${amount}đ"

                        val items = data.items ?: emptyList()
                        findViewById<TextView>(R.id.tvBadgeCount).text = "${items.size} món"

                        val rvItems = findViewById<RecyclerView>(R.id.rvItems)
                        rvItems.layoutManager = LinearLayoutManager(this@OrderDetailActivity)
                        rvItems.adapter = OrderDetailAdapter(items)
                    }
                } else {
                    Toast.makeText(this@OrderDetailActivity, "Không tải được chi tiết đơn", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<OrderDetailResponse>, t: Throwable) {
                Toast.makeText(this@OrderDetailActivity, "Lỗi kết nối", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun formatDate(dateString: String?): String {
        if (dateString == null) return ""
        return try {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            parser.timeZone = TimeZone.getTimeZone("UTC")
            val date = parser.parse(dateString)
            val formatter = SimpleDateFormat("HH:mm - dd/MM/yyyy", Locale.getDefault())
            formatter.format(date!!)
        } catch (e: Exception) {
            dateString
        }
    }
}

// Adapter render danh sách món ăn
class OrderDetailAdapter(private val items: List<OrderDetailItem>) : RecyclerView.Adapter<OrderDetailAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvItemName)
        val tvQuantity: TextView = view.findViewById(R.id.tvItemQuantity)
        val tvNote: TextView = view.findViewById(R.id.tvItemNote)
        val tvPrice: TextView = view.findViewById(R.id.tvItemPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order_detail, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvName.text = item.product_name
        holder.tvQuantity.text = "Số lượng: ${item.quantity}"

        if (item.notes.isNullOrEmpty()) {
            holder.tvNote.visibility = View.GONE
        } else {
            holder.tvNote.visibility = View.VISIBLE
            holder.tvNote.text = "Note: ${item.notes}"
        }

        val price = item.total_price?.replace(".00", "") ?: "0"
        holder.tvPrice.text = "${price}đ"
    }

    override fun getItemCount() = items.size
}