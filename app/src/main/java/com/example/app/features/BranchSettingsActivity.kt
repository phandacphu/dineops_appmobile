package com.example.app.features

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.app.R
import com.example.app.data.model.Branch

class BranchSettingsActivity : AppCompatActivity() {

    private lateinit var spinnerBranch: Spinner
    private lateinit var edtBranchName: EditText
    private lateinit var edtAddress: EditText
    private lateinit var edtPhone: EditText
    private lateinit var tvEditAll: TextView
    private lateinit var btnSave: Button

    private val branchList = mutableListOf<Branch>()
    private var selectedBranchIndex: Int = 0

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

        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }
    }

    private fun loadMockData() {
        if (branchList.isEmpty()) {
            branchList.add(Branch(1, "Main Street Branch", "123 Culinary Ave, NY", "+1 (555) 012-3456", 8.5, 10.0))
            branchList.add(Branch(2, "Downtown Branch", "456 Market St, CA", "+1 (555) 987-6543", 8.0, 5.0))
        }
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, branchList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerBranch.adapter = adapter

        spinnerBranch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedBranchIndex = position
                displayBranchInfo(branchList[position])
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
        tvEditAll.setOnClickListener { setEditingEnabled(true) }

        btnSave.setOnClickListener {
            val currentBranch = branchList[selectedBranchIndex]
            currentBranch.name = edtBranchName.text.toString()
            currentBranch.address = edtAddress.text.toString()
            currentBranch.phone = edtPhone.text.toString()

            setEditingEnabled(false)
            Toast.makeText(this, "Đã lưu thay đổi cho ${currentBranch.name}", Toast.LENGTH_SHORT).show()
            (spinnerBranch.adapter as ArrayAdapter<*>).notifyDataSetChanged()
        }

        findViewById<ImageButton>(R.id.btnAddBranch).setOnClickListener {
            showAddBranchDialog()
        }
    }

    private fun showAddBranchDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_branch, null)

        // Sửa lại ID cho đúng với file dialog_add_branch.xml
        val edtNewName = dialogView.findViewById<EditText>(R.id.edtNewBranchName)
        val edtNewAddr = dialogView.findViewById<EditText>(R.id.edtNewAddress)
        val edtNewPhon = dialogView.findViewById<EditText>(R.id.edtNewPhone)

        AlertDialog.Builder(this)
            .setTitle("Thêm chi nhánh")
            .setView(dialogView)
            .setPositiveButton("Thêm") { _, _ ->
                val name = edtNewName.text.toString().trim()
                val address = edtNewAddr.text.toString().trim()
                val phone = edtNewPhon.text.toString().trim()

                if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val newBranch = Branch(branchList.size + 1, name, address, phone, 0.0, 0.0)
                branchList.add(newBranch)
                (spinnerBranch.adapter as ArrayAdapter<*>).notifyDataSetChanged()
                spinnerBranch.setSelection(branchList.size - 1)

                Toast.makeText(this, "Đã thêm chi nhánh mới", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Huỷ", null)
            .create()
            .show()
    }

    private fun setEditingEnabled(enabled: Boolean) {
        edtBranchName.isEnabled = enabled
        edtAddress.isEnabled = enabled
        edtPhone.isEnabled = enabled
        btnSave.visibility = if (enabled) View.VISIBLE else View.GONE
    }
}
