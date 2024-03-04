package com.example.weichenglin_simplenewsapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weichenglin_simplenewsapp.api.NewsItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "NewsGalleryViewModel"
class NewsGalleryViewModel : ViewModel() {

    private val newsRepository = NewsRepository()

    private val _galleryItems: MutableStateFlow<List<NewsItem>> =
        MutableStateFlow(emptyList())
    val galleryItems: StateFlow<List<NewsItem>>
        get() = _galleryItems.asStateFlow()
    init {
        viewModelScope.launch {
            try {
                val items = newsRepository.fetchBusinessArticles("all")
                Log.d(TAG, "Items received: $items")
                _galleryItems.value = items
            } catch (ex: Exception) {
                Log.e(TAG, "Failed to fetch gallery items", ex)
            }
        }
    }

    fun setQuery(query: String) {
        viewModelScope.launch {
            try {
                Log.d(TAG, query)
                val bool = query == "all"
                Log.d(TAG, bool.toString())
                _galleryItems.value = fetchCategoryArticles(query)
                Log.d(TAG, "Items received: ${_galleryItems.value}")
            } catch (ex: Exception) {
                Log.e(TAG, "Failed to fetch gallery items", ex)
            }
             }
    }

    private suspend fun fetchCategoryArticles(query: String): List<NewsItem> {
        return if (query == "all") {
            newsRepository.fetchArticles()
        } else {
            newsRepository.fetchBusinessArticles(query)
        }
    }


}