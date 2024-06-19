package com.example.sepic.API

import android.os.Parcel
import android.os.Parcelable

data class AppData(
    val id: String?,
    val name: String?,
    val pencetus: String?,
    val genreImg: String?,
    val songImg: String?,
    val description: String?,
    val lirik: String?,
    val judul: String?,
    val pendengar: String?,
    val top: String?,
    val rating: String?
) : Parcelable {
    // Constructor used to create an object from Parcel
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    // Method used to write object to Parcel
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(id)
        dest.writeString(name)
        dest.writeString(pencetus)
        dest.writeString(genreImg)
        dest.writeString(songImg)
        dest.writeString(description)
        dest.writeString(lirik)
        dest.writeString(judul)
        dest.writeString(pendengar)
        dest.writeString(top)
        dest.writeString(rating)
    }

    // Method used to describe the contents of the Parcelable
    override fun describeContents(): Int {
        return 0
    }

    // Companion object to create the Parcelable
    companion object CREATOR : Parcelable.Creator<AppData> {
        // Method used to create an object from Parcel
        override fun createFromParcel(source: Parcel): AppData {
            return AppData(source)
        }

        // Method used to create an array of objects
        override fun newArray(size: Int): Array<AppData?> {
            return arrayOfNulls(size)
        }
    }
}
