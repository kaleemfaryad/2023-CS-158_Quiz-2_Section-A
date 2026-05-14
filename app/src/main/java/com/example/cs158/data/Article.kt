package com.example.cs158.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val title: String,
    val description: String,
    val content: String,
    val image: String,
    val publishedAt: String,
    val url: String,
    val source: Source
) : Parcelable

@Parcelize
data class Source(
    val name: String,
    val url: String
) : Parcelable

data class NewsResponse(
    val totalArticles: Int,
    val articles: List<Article>
)

