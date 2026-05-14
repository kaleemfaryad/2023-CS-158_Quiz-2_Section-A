package com.example.cs158.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cs158.R
import com.example.cs158.data.Article
import com.example.cs158.databinding.NewsItemBinding

class NewsAdapter(
    private var articles: List<Article> = emptyList()
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    fun updateNews(newArticles: List<Article>) {
        articles = newArticles
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    override fun getItemCount(): Int = articles.size

    inner class NewsViewHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.apply {
                newsImage.load(article.image)
                newsTitle.text = article.title
                newsSource.text = article.source.name
                newsDate.text = formatDate(article.publishedAt)
                root.setOnClickListener {
                    val intent = Intent(root.context, DetailActivity::class.java)
                    intent.putExtra("article", article)
                    root.context.startActivity(intent)
                }
            }
        }

        private fun formatDate(dateString: String): String {
            return try {
                dateString.substringBefore("T")
            } catch (e: Exception) {
                dateString
            }
        }
    }
}

