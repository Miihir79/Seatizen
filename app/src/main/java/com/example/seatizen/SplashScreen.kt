package com.example.seatizen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splashscreen.*

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        supportActionBar?.hide()
        val top_anim = AnimationUtils.loadAnimation(this,R.anim.top_anim)
        imageView.startAnimation(top_anim)
        Handler().postDelayed({
            val intent= Intent(this,LandingPage::class.java)
            startActivity(intent)
            finish()
        },1500)
    }
}