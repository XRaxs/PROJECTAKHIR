package com.example.sepic

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.ReturnMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.example.sepic.Room.ItemDatabase
import com.example.sepic.Room.ItemViewModel
import com.example.sepic.Room.ItemViewModelFactory
import com.example.sepic.Utils.reduceFileImage
import com.example.sepic.Utils.uriToFile
import com.google.android.material.textfield.TextInputEditText

class addRoom : AppCompatActivity() {

    private var currentImageUri: Uri? = null
    private lateinit var postViewModel: ItemViewModel
    private lateinit var vPostImage: ImageView
    private lateinit var vText_img: TextView
    private lateinit var vPostDesc: TextInputEditText
    private lateinit var vPostTitle: TextInputEditText
    private lateinit var vPostName: TextInputEditText
    private lateinit var vPostTop: TextInputEditText

    private val imagePickerLauncher = registerImagePicker {
        val firstImage = it.firstOrNull() ?: return@registerImagePicker
        if (firstImage.uri.toString().isNotEmpty()) {
            vPostImage.visibility = View.VISIBLE
            currentImageUri = firstImage.uri
            vText_img.text = "change"
            Glide.with(vPostImage)
                .load(firstImage.uri)
                .into(vPostImage)
        } else {
            vPostImage.visibility = View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_room)
        val factory = ItemViewModelFactory.getInstance(this)
        postViewModel = ViewModelProvider(this, factory)[ItemViewModel::class.java]

        vPostImage = findViewById(R.id.post_img_edit)
        vPostDesc = findViewById(R.id.post_desc_edit)
        vPostTitle = findViewById(R.id.post_judul_edit)
        vPostName = findViewById(R.id.post_name_edit)
        vPostTop = findViewById(R.id.post_top_edit)
        vText_img = findViewById(R.id.text_img)
        onClick()
    }

    private fun onClick() {
        vPostImage.setOnClickListener {
            imagePickerLauncher.launch(
                ImagePickerConfig {
                    mode = ImagePickerMode.SINGLE
                    returnMode = ReturnMode.ALL
                    isFolderMode = true
                    folderTitle = "Gallery"
                    isShowCamera = false
                    imageTitle = "Click to choose the image"
                    doneButtonText = "Done"
                }
            )
        }

        val btnSavedPlayer = findViewById<Button>(R.id.btn_savedPost)
        btnSavedPlayer.setOnClickListener {
            if (validateInput()) {
                savedData()
            }
        }
    }

    private fun validateInput(): Boolean {
        var error = 0

        if (vPostDesc.text.toString().isEmpty()) {
            error++
            vPostDesc.error = "Description is required!"
        }
        if (vPostTitle.text.toString().isEmpty()) {
            error++
            vPostTitle.error = "Title is required!"
        }
        if (vPostName.text.toString().isEmpty()) {
            error++
            vPostName.error = "Name is required!"
        }
        if (vPostTop.text.toString().isEmpty()) {
            error++
            vPostTop.error = "Top is required!"
        }
        if (currentImageUri == null) {
            error++
            vText_img.error = "Image is required!"
        }

        return error == 0
    }

    private fun savedData() {
        val imageFile = currentImageUri?.let { uriToFile(it, this).reduceFileImage() }

        val post = imageFile?.let {
            ItemDatabase(
                id = 0,
                title = vPostTitle.text.toString(),
                name = vPostName.text.toString(),
                description = vPostDesc.text.toString(),
                top = vPostTop.text.toString(),
                image = it,
            )
        }

        if (post != null) {
            postViewModel.insertItem(post)
            Toast.makeText(this@addRoom, "Data successfully added", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this@addRoom, "Failed to save data", Toast.LENGTH_SHORT).show()
        }
    }

    fun toMain(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
