package com.example.sepic

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Get data from intent
        val title = intent.getStringExtra("EXTRA_TITLE")
        val name = intent.getStringExtra("EXTRA_NAME")
        val top = intent.getStringExtra("EXTRA_TOP")
        val description = intent.getStringExtra("EXTRA_DESCRIPTION")
        val imageUri = intent.getStringExtra("EXTRA_IMAGE_URI")

        // Bind data to views
        findViewById<MaterialTextView>(R.id.title).text = title
        findViewById<MaterialTextView>(R.id.name).text = name
        findViewById<MaterialTextView>(R.id.top).text = top
        findViewById<MaterialTextView>(R.id.deskripsi).text = description
        findViewById<ShapeableImageView>(R.id.song_picture).setImageURI(Uri.parse(imageUri))

        // Find backIconDetail ImageView and set OnClickListener
        val backIconDetail = findViewById<ImageView>(R.id.backIconDetail)
        backIconDetail.setOnClickListener {
            // Navigate back to MainActivity
            val intent = Intent(this@DetailActivity, ProfilActivity::class.java)
            startActivity(intent)
            finish() // Optional: finish ProfilActivity if you don't want it in the back stack
        }
    }
}
