package com.example.apicalls

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.apicalls.MainActivity
import com.example.apicalls.R

class SplashScreen : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var loadingText: TextView
    private var progressStatus = 0
    private val handler = Handler(Looper.getMainLooper())

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        progressBar = findViewById(R.id.progressBar)
        Thread {
            while (progressStatus < 100) {
                progressStatus += 10
                handler.post {
                    progressBar.progress = progressStatus

                }
                Thread.sleep(300) // Adjust speed of progress bar
            }
            // Redirect to MainActivity after progress is complete
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }.start()
    }
}
