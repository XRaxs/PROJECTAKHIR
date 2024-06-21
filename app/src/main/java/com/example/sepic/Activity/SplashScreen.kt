package com.example.sepic

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {
    private val SPLASH_DISPLAY_LENGTH = 2000L // Durasi tampilan splash screen (3 detik)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.VISIBLE // Tampilkan ProgressBar

        Handler().postDelayed({
            progressBar.visibility = View.GONE
            val intent = Intent(
                this@SplashScreen,
                MainActivity::class.java
            )
            startActivity(intent)
            finish()
        }, SPLASH_DISPLAY_LENGTH);
    }
}