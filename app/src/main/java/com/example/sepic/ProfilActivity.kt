package com.example.sepic

import PopUp
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sepic.Adapter.ItemAdapterRoom
import com.example.sepic.Room.ItemDatabase
import com.example.sepic.Room.ItemViewModel
import com.example.sepic.Room.ItemViewModelFactory

class ProfilActivity : AppCompatActivity() {

    private lateinit var itemViewModel: ItemViewModel
    private lateinit var itemAdapterRoom: ItemAdapterRoom
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profil)

        val factory = ItemViewModelFactory.getInstance(this)
        itemViewModel = ViewModelProvider(this, factory)[ItemViewModel::class.java]
        recyclerView = findViewById(R.id.rvData)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Default menampilkan kategori pertama saat activity dibuka
        loadCategory("Category 1")

        // Find backIconDetail ImageView and set OnClickListener
        val backIconDetail = findViewById<ImageView>(R.id.backIconDetail)
        backIconDetail.setOnClickListener {
            // Navigate back to MainActivity
            val intent = Intent(this@ProfilActivity, MainActivity::class.java)
            startActivity(intent)
            finish() // Optional: finish ProfilActivity if you don't want it in the back stack
        }
    }

    private fun loadCategory(category: String) {
        itemViewModel.getAllItem().observe(this) { postData ->
            if (postData != null) {
                itemAdapterRoom = ItemAdapterRoom(postData, itemViewModel)
                recyclerView.adapter = itemAdapterRoom

                itemAdapterRoom.setOnItemClickCallback(object :
                    ItemAdapterRoom.OnItemClickCallback {
                    override fun onMoreClicked(data: ItemDatabase, position: Int) {
                        PopUp(data, position).show(supportFragmentManager, PopUp.TAG)
                    }
                })
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        // Refresh data saat activity di-restart
        loadCategory("Category 1")
    }

    fun toAddPost(view: View) {
        val intent = Intent(this, addRoom::class.java)
        startActivity(intent)
    }
}
