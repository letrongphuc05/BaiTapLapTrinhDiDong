package com.example.baitapvenhatuan2// Thay theo package dự án của bạn

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var txtName: EditText
    private lateinit var txtAge: EditText
    private lateinit var btnCheck: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo biến
        txtName = findViewById(R.id.txtName)
        txtAge = findViewById(R.id.txtAge)
        btnCheck = findViewById(R.id.btnCheck)

        // Xử lý nút kiểm tra
        btnCheck.setOnClickListener {
            val name = txtName.text.toString().trim()
            val ageStr = txtAge.text.toString().trim()

            if (name.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập Họ Và Tên", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (ageStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập Tuổi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val age = ageStr.toIntOrNull()
            if (age == null || age < 0 || age == 0) {
                Toast.makeText(this, "Tuổi không hợp lệ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val result = when {
                age > 65 && age < 120 -> "Người già"
                age in 6..65 -> "Người lớn"
                age in 2..6 -> "Trẻ em"
                age in 0..2 -> "Em bé"
                else -> "Tuổi không hợp lệ"
            }

            Toast.makeText(this, "Chào $name! Bạn $age tuổi. $result", Toast.LENGTH_LONG).show()
        }
    }
}