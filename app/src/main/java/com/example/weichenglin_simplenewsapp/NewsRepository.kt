package com.example.weichenglin_simplenewsapp

import com.example.weichenglin_simplenewsapp.api.NewsApi
import com.example.weichenglin_simplenewsapp.api.NewsInterceptor
import com.example.weichenglin_simplenewsapp.api.NewsItem
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class NewsRepository {
    private val newsApi: NewsApi

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(NewsInterceptor())
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
        newsApi = retrofit.create()
    }

    suspend fun fetchArticles() : List<NewsItem> {
        return newsApi.fetchAllArticles().articles
    }

    suspend fun fetchBusinessArticles(query: String) : List<NewsItem> {
        return newsApi.fetchBusinessArticles(query).articles
    }
}