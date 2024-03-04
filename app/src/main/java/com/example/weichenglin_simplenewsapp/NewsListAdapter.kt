package com.example.weichenglin_simplenewsapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weichenglin_simplenewsapp.api.NewsItem
import com.example.weichenglin_simplenewsapp.databinding.ListItemGalleryBinding

class NewsViewHolder(
    private val binding: ListItemGalleryBinding
) : RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bind (newsItem: NewsItem, onNewsClicked: (title: String?, url: String?, content: String?) -> Unit) {
        binding.body.text = newsItem.description
        binding.title.text = newsItem.title
        binding.headerImage.load(newsItem.urlToImage) {
            placeholder(R.drawable.ic_launcher_foreground)
        }

        binding.root.setOnClickListener {
            onNewsClicked(newsItem.title, newsItem.urlToImage, newsItem.content)
        }
    }
}


class NewsListAdapter(
    private val newsItems: List<NewsItem>,
    private val onNewsClicked: (title: String?, url: String?, content: String?) -> Unit
) : RecyclerView.Adapter<NewsViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemGalleryBinding.inflate(inflater, parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return newsItems.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = newsItems[position]
        holder.bind(item, onNewsClicked)
    }


}