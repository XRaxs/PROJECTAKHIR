package com.example.sepic.Room

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.File

@Entity
data class ItemDatabase(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "song_title")
    val title: String,

    @ColumnInfo(name = "artist_name")
    val name: String,

    @ColumnInfo(name = "desc")
    val description: String,

    @ColumnInfo(name = "top")
    val top: String,

    @ColumnInfo(name = "song_image")
    val image: File

//mengirim data ketika inten putExtra
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        File(parcel.readString()!!)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(top)
        parcel.writeString(image.path)
    }

    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR : Parcelable.Creator<ItemDatabase> {
        override fun createFromParcel(parcel: Parcel): ItemDatabase {
            return ItemDatabase(parcel)
        }

        override fun newArray(size: Int): Array<ItemDatabase?> {
            return arrayOfNulls(size)
        }
    }
}