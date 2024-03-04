package com.example.weichenglin_simplenewsapp.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsItem(
    val source: Source? = null,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)
