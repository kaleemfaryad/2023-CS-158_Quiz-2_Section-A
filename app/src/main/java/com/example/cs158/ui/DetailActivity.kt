package com.example.cs158.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.cs158.R
import com.example.cs158.data.Article

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val article = intent.getParcelableExtra<Article>("article")

        if (article != null) {
            findViewById<ImageView>(R.id.detailImage).load(article.image)
            findViewById<TextView>(R.id.detailTitle).text = article.title
            findViewById<TextView>(R.id.detailSource).text = "Source: ${article.source.name}"
            findViewById<TextView>(R.id.detailDate).text = "Published: ${formatDate(article.publishedAt)}"
            findViewById<TextView>(R.id.detailDescription).text = article.description
            findViewById<TextView>(R.id.detailContent).text = article.content

            findViewById<Button>(R.id.readFullButton).setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(article.url)))
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

