package com.example.sepic.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.sepic.R

class SplashScreen : AppCompatActivity() {
    private val SPLASH_DISPLAY_LENGTH = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            val intent = Intent(
                this@SplashScreen,
                MainActivity::class.java
            )
            startActivity(intent)
            finish()
        }, SPLASH_DISPLAY_LENGTH);
    }
}