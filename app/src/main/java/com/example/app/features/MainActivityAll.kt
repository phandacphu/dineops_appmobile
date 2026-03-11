package com.example.app.features

<<<<<<< HEAD
import android.content.Intent
import android.os.Bundle
import android.view.View
=======
import android.os.Bundle
>>>>>>> e60e341f603258cc3534cac0c10d2dcf96724631
import androidx.appcompat.app.AppCompatActivity
import com.example.app.R

class MainActivityAll : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_all)
<<<<<<< HEAD

        // Mở Kho hàng
        val btnWarehouse = findViewById<View>(R.id.cardWarehouse)
        btnWarehouse.setOnClickListener {
            startActivity(Intent(this, WarehouseActivity::class.java))
        }

        // Mở Nhân viên
        val cardStaff = findViewById<View>(R.id.cardStaff)
        cardStaff.setOnClickListener {
            startActivity(Intent(this, StaffActivity::class.java))
        }

        // Mở Thực đơn
        val cardMenu = findViewById<View>(R.id.cardMenu)
        cardMenu.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        // Mở Chi nhánh
        val cardBranch = findViewById<View>(R.id.cardBranch)
        cardBranch.setOnClickListener {
            startActivity(Intent(this, BranchSettingsActivity::class.java))
        }
=======
>>>>>>> e60e341f603258cc3534cac0c10d2dcf96724631
    }
}
