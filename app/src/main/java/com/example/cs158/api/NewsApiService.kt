package com.example.cs158.api

import com.example.cs158.data.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("top")
    suspend fun getTopHeadlines(
        @Query("category") category: String = "general",
        @Query("lang") lang: String = "en",
        @Query("country") country: String,
        @Query("max") max: Int = 10,
        @Query("apikey") apikey: String
    ): NewsResponse
}

