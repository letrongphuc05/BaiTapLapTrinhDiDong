package com.example.thuchanh1
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var textViewToChange: TextView
    private lateinit var actionButton: Button
    private lateinit var nameTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nameTextView = findViewById(R.id.textView)
        textViewToChange = findViewById(R.id.textView2)
        actionButton = findViewById(R.id.button)
        actionButton.setOnClickListener {
            textViewToChange.text = "Lê Trọng Phúc"
        }
    }
}