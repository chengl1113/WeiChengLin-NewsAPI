package com.example.weichenglin_simplenewsapp.api

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines?country=us&pageSize=100&apiKey=f229ef3749474f50b356e2f04a169a85")
    suspend fun fetchAllArticles(): NewsApiResponse

    @GET("top-headlines?country=us&pageSize=100")
    suspend fun fetchBusinessArticles(@Query("category") query: String): NewsApiResponse
}