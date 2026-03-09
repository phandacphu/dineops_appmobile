package com.example.app.features

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.app.R

class BranchSettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Kết nối với file XML giao diện cài đặt chi nhánh
        setContentView(R.layout.activity_branch_settings)
    }
}