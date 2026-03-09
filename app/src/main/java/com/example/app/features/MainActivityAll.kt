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

        val btnWarehouse = findViewById<View>(R.id.cardWarehouse)

        // 2. Thiết lập sự kiện bấm nút
        btnWarehouse.setOnClickListener {
            val intent = Intent(this, WarehouseActivity::class.java)
            startActivity(intent)
        }
=======
>>>>>>> e60e341f603258cc3534cac0c10d2dcf96724631
    }
}