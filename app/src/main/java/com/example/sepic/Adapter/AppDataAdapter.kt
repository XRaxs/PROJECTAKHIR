package com.example.sepic.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sepic.API.AppData
import com.example.sepic.DetailActivityGenre
import com.example.sepic.DetailActivityMusic
import com.example.sepic.R
import com.google.android.material.imageview.ShapeableImageView

class AppDataAdapter(private var dataList: List<AppData>, private val viewType: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_GENRE = 0
        const val TYPE_SONG = 1
    }

    open class AppDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class GenreViewHolder(itemView: View) : AppDataViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val genreImg: ShapeableImageView = itemView.findViewById(R.id.genre_picture)
        val pencetus: TextView = itemView.findViewById(R.id.pencetus)
    }

    class SongViewHolder(itemView: View) : AppDataViewHolder(itemView) {
        val musicName: TextView = itemView.findViewById(R.id.tittle)
        val nameArtist: TextView = itemView.findViewById(R.id.name)
        val songImg: ShapeableImageView = itemView.findViewById(R.id.song_picture)
        val top: TextView = itemView.findViewById(R.id.top)
        val pendengar: TextView = itemView.findViewById(R.id.pendengar)
        val rating: TextView = itemView.findViewById(R.id.rating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_GENRE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
                GenreViewHolder(view)
            }
            TYPE_SONG -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_songs, parent, false)
                SongViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = dataList[position]
        when (holder) {
            is GenreViewHolder -> {
                holder.name.text = data.name
                holder.pencetus.text = "Pencetus: ${data.pencetus}"
                Glide.with(holder.itemView.context)
                    .load(data.genreImg)
                    .into(holder.genreImg)
                holder.itemView.setOnClickListener {
                    val intent = Intent(holder.itemView.context, DetailActivityGenre::class.java)
                    intent.putExtra("appData", data)
                    holder.itemView.context.startActivity(intent)
                }
            }
            is SongViewHolder -> {
                holder.musicName.text = data.judul
                holder.nameArtist.text = data.name
                holder.top.text = data.top
                holder.pendengar.text = data.pendengar
                holder.rating.text = data.rating
                Glide.with(holder.itemView.context)
                    .load(data.songImg)
                    .into(holder.songImg)
                holder.itemView.setOnClickListener {
                    val intent = Intent(holder.itemView.context, DetailActivityMusic::class.java)
                    intent.putExtra("appData", data)
                    holder.itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    override fun getItemCount() = dataList.size

    fun updateData(newData: List<AppData>) {
        dataList = newData
        notifyDataSetChanged()
    }
}
