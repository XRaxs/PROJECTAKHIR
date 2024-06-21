package com.example.sepic.Adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sepic.Activity.DetailActivity
import com.example.sepic.R
import com.example.sepic.Room.ItemDatabase
import com.example.sepic.Room.ItemViewModel
import com.google.android.material.imageview.ShapeableImageView

class ItemAdapterRoom(private var itemList: List<ItemDatabase>, private val itemViewModel: ItemViewModel) :
    RecyclerView.Adapter<ItemAdapterRoom.ItemViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onMoreClicked(data: ItemDatabase, position: Int)
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val name: TextView = itemView.findViewById(R.id.name)
        val top: TextView = itemView.findViewById(R.id.top)
        val postImg: ShapeableImageView = itemView.findViewById(R.id.song_picture)
        val btnMore: ImageView = itemView.findViewById(R.id.btn_more)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_bookmark, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = itemList[position]

        holder.title.text = data.title
        holder.name.text = data.name
        holder.top.text = ("#${data.top}")

        val uri = Uri.fromFile(data.image)
        holder.postImg.setImageURI(uri)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java).apply {
                putExtra("EXTRA_TITLE", data.title)
                putExtra("EXTRA_NAME", data.name)
                putExtra("EXTRA_TOP", data.top)
                putExtra("EXTRA_DESCRIPTION", data.description)
                putExtra("EXTRA_IMAGE_URI", data.image.toString())
            }
            holder.itemView.context.startActivity(intent)
        }
        holder.btnMore.setOnClickListener {
            onItemClickCallback.onMoreClicked(itemList[holder.absoluteAdapterPosition], holder.absoluteAdapterPosition)
        }
    }

    override fun getItemCount(): Int = itemList.size

    fun updateItems(items: List<ItemDatabase>) {
        itemList = items
        notifyDataSetChanged()
    }
}
