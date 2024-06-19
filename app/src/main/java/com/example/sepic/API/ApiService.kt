package com.example.sepic.API

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("genre")
    fun getGenres(): Call<List<AppData>>

    @GET("TopMusic")
    fun getSongs(): Call<List<AppData>>
}
