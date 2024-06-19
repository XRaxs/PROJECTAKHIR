package com.example.sepic

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sepic.API.ViewModel
import com.example.sepic.Adapter.AppDataAdapter
import com.google.android.material.imageview.ShapeableImageView

class MainActivity : AppCompatActivity() {

    private val viewModel: ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val genreRecyclerView = findViewById<RecyclerView>(R.id.rv_genre)
        val songRecyclerView = findViewById<RecyclerView>(R.id.rv_song)
        val imageProfile = findViewById<ShapeableImageView>(R.id.imageProfile)

        genreRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        songRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val genreAdapter = AppDataAdapter(emptyList(), AppDataAdapter.TYPE_GENRE)
        val songAdapter = AppDataAdapter(emptyList(), AppDataAdapter.TYPE_SONG)

        genreRecyclerView.adapter = genreAdapter
        songRecyclerView.adapter = songAdapter

        viewModel.genres.observe(this, Observer { genres ->
            genreAdapter.updateData(genres)
        })

        viewModel.songs.observe(this, Observer { songs ->
            songAdapter.updateData(songs)
        })

        viewModel.fetchGenres()
        viewModel.fetchSongs()
        imageProfile.setOnClickListener {
            val intent = Intent(this, ProfilActivity::class.java)
            startActivity(intent)
        }
    }
}
