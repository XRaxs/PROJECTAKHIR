package com.example.sepic

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.sepic.API.AppData
import com.example.sepic.databinding.ActivityDetailGenreBinding
import com.example.sepic.databinding.ActivityDetailMusicBinding

class DetailActivityGenre : AppCompatActivity() {
    private lateinit var binding: ActivityDetailGenreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailGenreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appData = intent.getParcelableExtra<AppData>("appData")

        appData?.let {
            binding.genre.text = it.name
            binding.pencetus.text = it.pencetus
            binding.deskripsi.text = it.description

            val imageUrl = it.songImg ?: it.genreImg // Fallback to genreImg if songImg is null
            Glide.with(this)
                .load(imageUrl)
                .into(binding.gambar)
        }
        binding.backIconDetail.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish() // Optional: memanggil finish jika ingin menutup aktivitas saat ini
        }
        binding.imageProfile.setOnClickListener {
            val intent = Intent(this, ProfilActivity::class.java)
            startActivity(intent)
        }
    }
}