package com.example.app.features

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.app.R
import com.example.app.data.model.Branch

class BranchActivity : AppCompatActivity() {

    private lateinit var spinnerBranch: Spinner
    private lateinit var edtBranchName: EditText
    private lateinit var edtAddress: EditText
    private lateinit var edtPhone: EditText
    private lateinit var tvEditAll: TextView
    private lateinit var btnSave: Button
    private lateinit var btnAddBranch: ImageButton

    private val branchList = mutableListOf<Branch>()
    private lateinit var adapter: ArrayAdapter<Branch>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_branch_settings)

        initViews()
        loadMockData()
        setupSpinner()
        handleEvents()
    }

    private fun initViews() {
        spinnerBranch = findViewById(R.id.spinnerBranch)
        edtBranchName = findViewById(R.id.edtBranchName)
        edtAddress = findViewById(R.id.edtAddress)
        edtPhone = findViewById(R.id.edtPhone)
        tvEditAll = findViewById(R.id.tvEditAll)
        btnSave = findViewById(R.id.btnSave)
        btnAddBranch = findViewById(R.id.btnAddBranch)

        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }
    }

    private fun loadMockData() {
        if (branchList.isEmpty()) {
            branchList.add(Branch(1, "Main Street Branch", "123 Culinary Ave, NY", "+1 (555) 012-3456", 8.5, 10.0))
            branchList.add(Branch(2, "Downtown Branch", "456 Market St, CA", "+1 (555) 987-6543", 8.0, 5.0))
        }
    }

    private fun setupSpinner() {
        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, branchList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerBranch.adapter = adapter

        spinnerBranch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selected = branchList[position]
                displayBranchInfo(selected)
                setEditingEnabled(false)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun displayBranchInfo(branch: Branch) {
        edtBranchName.setText(branch.name)
        edtAddress.setText(branch.address)
        edtPhone.setText(branch.phone)
    }

    private fun handleEvents() {
        tvEditAll.setOnClickListener {
            setEditingEnabled(true)
            edtBranchName.requestFocus()
            Toast.makeText(this, "Chế độ chỉnh sửa đã bật", Toast.LENGTH_SHORT).show()
        }

        btnSave.setOnClickListener {
            val position = spinnerBranch.selectedItemPosition
            if (position != -1) {
                val newName = edtBranchName.text.toString().trim()
                if (newName.isEmpty()) {
                    edtBranchName.error = "Tên không được để trống"
                    return@setOnClickListener
                }

                branchList[position].apply {
                    name = newName
                    address = edtAddress.text.toString().trim()
                    phone = edtPhone.text.toString().trim()
                }

                adapter.notifyDataSetChanged()
                setEditingEnabled(false)
                Toast.makeText(this, "Đã lưu thay đổi thành công!", Toast.LENGTH_SHORT).show()
            }
        }

        // Đảm bảo nút thêm chi nhánh hoạt động
        btnAddBranch.setOnClickListener {
            showAddBranchDialog()
        }
    }

    private fun showAddBranchDialog() {
        try {
            val view = layoutInflater.inflate(R.layout.dialog_add_branch, null)
            val nameIn = view.findViewById<EditText>(R.id.edtNewBranchName)
            val addrIn = view.findViewById<EditText>(R.id.edtNewAddress)
            val phoneIn = view.findViewById<EditText>(R.id.edtNewPhone)

            AlertDialog.Builder(this)
                .setTitle("Thêm chi nhánh mới")
                .setView(view)
                .setPositiveButton("Thêm") { _, _ ->
                    val name = nameIn.text.toString().trim()
                    val addr = addrIn.text.toString().trim()
                    val phone = phoneIn.text.toString().trim()
                    
                    if (name.isNotEmpty()) {
                        val newBranch = Branch(branchList.size + 1, name, addr, phone, 0.0, 0.0)
                        branchList.add(newBranch)
                        adapter.notifyDataSetChanged()
                        spinnerBranch.setSelection(branchList.size - 1)
                        Toast.makeText(this, "Đã thêm chi nhánh: $name", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Tên chi nhánh không được để trống", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Hủy", null)
                .create()
                .show()
        } catch (e: Exception) {
            Toast.makeText(this, "Lỗi: Không thể mở hộp thoại thêm chi nhánh", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    private fun setEditingEnabled(enabled: Boolean) {
        edtBranchName.isEnabled = enabled
        edtAddress.isEnabled = enabled
        edtPhone.isEnabled = enabled
        btnSave.visibility = if (enabled) View.VISIBLE else View.GONE
    }
}
