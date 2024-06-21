package com.example.sepic.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.sepic.API.AppData
import com.example.sepic.databinding.ActivityDetailMusicBinding

class DetailActivityMusic : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMusicBinding
    private val appViewModel: ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appData = intent.getParcelableExtra<AppData>("appData")

        appData?.let {
            binding.nama.text = it.name
            binding.judul.text = it.judul
            binding.pendengar.text = it.pendengar
            binding.top.text = it.top
            binding.rating.text = it.rating
            binding.lirik.text = it.lirik

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
