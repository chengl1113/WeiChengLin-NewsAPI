package com.example.weichenglin_simplenewsapp.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsApiResponse(
  @Json(name="articles") val articles: List<NewsItem>
)
