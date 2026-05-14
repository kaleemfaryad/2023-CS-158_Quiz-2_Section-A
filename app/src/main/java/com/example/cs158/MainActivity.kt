package com.example.cs158

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.example.cs158.api.RetrofitClient
import com.example.cs158.ui.NewsAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorText: TextView
    private lateinit var countrySpinner: Spinner
    private lateinit var refreshButton: Button
    private val adapter = NewsAdapter()

    private val countryMap = mapOf(
        "Pakistan" to "pk",
        "United States" to "us",
        "United Kingdom" to "gb",
        "India" to "in",
        "Saudi Arabia" to "sa",
        "UAE" to "ae"
    )

    // Note: Replace with your actual API key from gnews.io
    private val API_KEY = "8e0e6f18fb89419c1b8dc968cf2eb60a"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.newsRecyclerView)
        progressBar = findViewById(R.id.progressBar)
        errorText = findViewById(R.id.errorText)
        countrySpinner = findViewById(R.id.countrySpinner)
        refreshButton = findViewById(R.id.refreshButton)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Setup country spinner
        val countries = countryMap.keys.toList()
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countries)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        countrySpinner.adapter = spinnerAdapter

        // Load news on country selection change
        countrySpinner.setOnItemSelectedListener(object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                fetchNews()
            }

            override fun onNothingSelected(parent: android.widget.AdapterView<*>) {}
        })

        refreshButton.setOnClickListener {
            fetchNews()
        }

        // Load initial news
        fetchNews()
    }

    private fun fetchNews() {
        val selectedCountry = countrySpinner.selectedItem as String
        val countryCode = countryMap[selectedCountry] ?: "us"

        progressBar.visibility = android.view.View.VISIBLE
        errorText.visibility = android.view.View.GONE

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getTopHeadlines(
                    country = countryCode,
                    apikey = API_KEY
                )
                adapter.updateNews(response.articles)
                progressBar.visibility = android.view.View.GONE
            } catch (e: Exception) {
                progressBar.visibility = android.view.View.GONE
                errorText.visibility = android.view.View.VISIBLE
                errorText.text = "Error: ${e.message ?: "Unknown error"}"
            }
        }
    }
}

