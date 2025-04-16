package com.example.apicalls

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView

class FourthPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fourth_page)

        val animatedSun = findViewById<LottieAnimationView>(R.id.animated_sun)
        val switchToSolar=findViewById<SwitchCompat>(R.id.switchToSolar)
        val cardSolarData = findViewById<CardView>(R.id.card_wind_data)

        animatedSun.playAnimation()

        val animator = ObjectAnimator.ofFloat(animatedSun, "translationX", -200f, 50f)
        animator.duration = 2000
        animator.start()

        switchToSolar.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                switchToSolar.text = "Switch To Solar"

                val fadeOut = ObjectAnimator.ofFloat(switchToSolar, "alpha", 1f, 0f)
                fadeOut.duration = 300
                fadeOut.start()

                fadeOut.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationEnd(animation: Animator) {
                        val intent = Intent(this@FourthPage, ThirdPage::class.java)
                        startActivity(intent)
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out) // Smooth transition
                        finish()
                    }
                    override fun onAnimationStart(animation: Animator) {}
                    override fun onAnimationCancel(animation: Animator) {}
                    override fun onAnimationRepeat(animation: Animator) {}
                })
            } else {
                switchToSolar.text = "Switch To Wind"
            }
        }
        cardSolarData.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}