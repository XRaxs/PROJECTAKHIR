package com.example.sepic

import com.example.sepic.PopUp
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import java.io.File

class UpdateItemRoomActivity : AppCompatActivity() {
    private var currentImageUri: Uri? = null
    private var oldPhoto: File? = null

    private lateinit var itemViewModel: ItemViewModel
    private lateinit var vPostDesc: TextInputEditText
    private lateinit var vPostImage: ImageView
    private lateinit var vPostTitle: TextInputEditText
    private lateinit var vPostName: TextInputEditText
    private lateinit var vPostTop: TextInputEditText
    private lateinit var vText_img: TextView
    private lateinit var getData: ItemDatabase

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
        setContentView(R.layout.activity_update_item_room)

        getData = intent.getParcelableExtra("post")!!
        val postViewModelFactory = ItemViewModelFactory.getInstance(this)
        itemViewModel = ViewModelProvider(this, postViewModelFactory)[ItemViewModel::class.java]

        vPostImage = findViewById(R.id.post_img_update)
        vPostDesc = findViewById(R.id.post_desc_update)
        vPostTitle = findViewById(R.id.post_judul_update)
        vPostName = findViewById(R.id.post_name_update)
        vPostTop = findViewById(R.id.post_top_update)
        vText_img = findViewById(R.id.text_img_update)

        vPostDesc.setText(getData.description)
        vPostTitle.setText(getData.title)
        vPostName.setText(getData.name)
        vPostTop.setText(getData.top)
        vText_img.text = "change"

        oldPhoto = getData.image
        Glide.with(vPostImage)
            .load(getData.image)
            .into(vPostImage)

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

        val btnMore = findViewById<ImageView>(R.id.btn_more)
        btnMore?.setOnClickListener {
            val position = 0
            val popUp = PopUp(getData, position)
            popUp.show(supportFragmentManager, PopUp.TAG)
        }


        val btnSavePost = findViewById<Button>(R.id.btn_updatedPost)
        btnSavePost.setOnClickListener {
            if (validateInput()) {
                saveData()
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
        if (currentImageUri == null && oldPhoto == null) {
            error++
            vText_img.error = "Image is required!"
        }

        return error == 0
    }

    private fun saveData() {
        val imageFile = currentImageUri?.let { uriToFile(it, this).reduceFileImage() }

        val item = (if (currentImageUri != null) imageFile else oldPhoto)?.let {
            ItemDatabase(
                id = getData.id,
                title = vPostTitle.text.toString(),
                name = vPostName.text.toString(),
                description = vPostDesc.text.toString(),
                top = vPostTop.text.toString(),
                image = it,
            )
        }

        if (item != null) {
            Log.d("UpdatePostActivity", "Post Data: $item")
            itemViewModel.updateItem(item)
            Toast.makeText(this@UpdateItemRoomActivity, "Data successfully updated", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this@UpdateItemRoomActivity, "Failed to update data", Toast.LENGTH_SHORT).show()
        }
    }

    fun toMain(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
