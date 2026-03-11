package com.example.app.features

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText

class StaffActivity : AppCompatActivity() {

    private lateinit var adapterWorking: StaffAdapter
    private lateinit var adapterOff: StaffAdapter

    private var workingList = arrayListOf<Staff>()
    private var offList = arrayListOf<Staff>()

    private val addStaffLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val name = result.data?.getStringExtra("NEW_NAME") ?: ""
            val role = result.data?.getStringExtra("NEW_ROLE") ?: ""
            if (name.isNotEmpty()) {
                val newStaff = Staff(name, role, "Mới thêm", "Đang làm", "Chưa phân công")
                workingList.add(newStaff)
                adapterWorking.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff)

        workingList.addAll(listOf(
            Staff("Sarah Jenkins", "Thu ngân", "7h-12h", "Đang làm", "Quầy thu ngân"),
            Staff("Marco Rossi", "Bếp trưởng", "12h-17h", "Đang làm", "Bếp")
        ))
        offList.addAll(listOf(
            Staff("David Chen", "Phục vụ", "17h-22h", "Nghỉ", "Khu A"),
            Staff("Elena Gomez", "Thu ngân", "Ngày nghỉ", "Nghỉ", "Chưa phân công")
        ))

        val rvWorking = findViewById<RecyclerView>(R.id.rvStaffWorking)
        adapterWorking = StaffAdapter(workingList, { _, _ -> }, { staff -> showAssignmentDialog(staff) })
        rvWorking.layoutManager = LinearLayoutManager(this)
        rvWorking.adapter = adapterWorking

        val rvOff = findViewById<RecyclerView>(R.id.rvStaffOff)
        adapterOff = StaffAdapter(offList, { _, _ -> }, { staff -> showAssignmentDialog(staff) })
        rvOff.layoutManager = LinearLayoutManager(this)
        rvOff.adapter = adapterOff

        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<Button>(R.id.btnAddStaff).setOnClickListener {
            val intent = Intent(this, AddStaffActivity::class.java)
            addStaffLauncher.launch(intent)
        }
    }

    private fun showAssignmentDialog(staff: Staff) {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_assignment, null)
        dialog.setContentView(view)

        // Ánh xạ View
        val editName = view.findViewById<TextInputEditText>(R.id.editName)
        val dropdownShift = view.findViewById<AutoCompleteTextView>(R.id.dropdownShift)
        val dropdownArea = view.findViewById<AutoCompleteTextView>(R.id.dropdownArea)
        val dropdownRole = view.findViewById<AutoCompleteTextView>(R.id.dropdownRole)
        val btnSave = view.findViewById<Button>(R.id.btnSave)
        val btnDelete = view.findViewById<Button>(R.id.btnDelete)
        val btnBack = view.findViewById<ImageButton>(R.id.btnBack)

        // Gán dữ liệu cũ để khi mở Dialog nó hiện đúng cái đang có
        editName.setText(staff.name)
        dropdownShift.setText(staff.info, false)
        dropdownRole.setText(staff.role, false)
        dropdownArea.setText(staff.area, false) // <--- BỔ SUNG NÀY: Để hiện khu vực hiện tại

        // Thiết lập Adapter cho các Dropdown
        val adapterShift = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, listOf("7h-12h", "12h-17h", "17h-22h"))
        dropdownShift.setAdapter(adapterShift)

        val adapterArea = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, listOf("Khu A", "Khu B", "Quầy thu ngân", "Bếp"))
        dropdownArea.setAdapter(adapterArea)

        val adapterRole = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, listOf("Bếp trưởng", "Phụ bếp", "Thu ngân", "Quản lý", "Phục vụ", "Bảo vệ"))
        dropdownRole.setAdapter(adapterRole)

        btnBack.setOnClickListener { dialog.dismiss() }

        btnDelete.setOnClickListener {
            workingList.remove(staff)
            offList.remove(staff)
            adapterWorking.notifyDataSetChanged()
            adapterOff.notifyDataSetChanged()
            dialog.dismiss()
        }

        btnSave.setOnClickListener {
            // LƯU DỮ LIỆU
            staff.name = editName.text.toString()
            staff.info = dropdownShift.text.toString()
            staff.role = dropdownRole.text.toString()
            staff.area = dropdownArea.text.toString() // <--- BỔ SUNG NÀY: Gán khu vực mới vào object

            // CẬP NHẬT UI
            adapterWorking.notifyDataSetChanged()
            adapterOff.notifyDataSetChanged()
            dialog.dismiss()
        }

        dialog.show()
    }
}