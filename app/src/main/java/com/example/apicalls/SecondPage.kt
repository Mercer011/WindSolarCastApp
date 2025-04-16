package com.example.apicalls

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SecondPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_page)

        val btnGoToThirdPage = findViewById<Button>(R.id.loginButton)
        btnGoToThirdPage.setOnClickListener {
            val intent = Intent(this, ThirdPage::class.java)
            startActivity(intent)
        }
    }
}