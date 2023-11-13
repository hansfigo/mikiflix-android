package com.example.mikiflix

import android.telecom.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("trending")
    suspend fun getData(): AnimeResponse

    @GET("info/{animeId}")
    suspend fun getAnimeInfo(@Path("animeId") animeId: String) : AnimeInfo
}