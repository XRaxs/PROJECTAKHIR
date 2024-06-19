package com.example.sepic.API

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModel : ViewModel() {

    private val _genres = MutableLiveData<List<AppData>>()
    val genres: LiveData<List<AppData>> get() = _genres

    private val _songs = MutableLiveData<List<AppData>>()
    val songs: LiveData<List<AppData>> get() = _songs

    fun fetchGenres() {
        ApiConfig.instance.getGenres().enqueue(object : Callback<List<AppData>> {
            override fun onResponse(call: Call<List<AppData>>, response: Response<List<AppData>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _genres.value = it
                    }
                }
            }

            override fun onFailure(call: Call<List<AppData>>, t: Throwable) {
                // Handle failure
            }
        })
    }

    fun fetchSongs() {
        ApiConfig.instance.getSongs().enqueue(object : Callback<List<AppData>> {
            override fun onResponse(call: Call<List<AppData>>, response: Response<List<AppData>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _songs.value = it
                    }
                }
            }

            override fun onFailure(call: Call<List<AppData>>, t: Throwable) {
                // Handle failure
            }
        })
    }
}
