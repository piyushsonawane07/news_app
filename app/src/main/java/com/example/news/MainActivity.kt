package com.example.news

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var mAdapter: NewsListAdapter
    private var category:String = "business"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchData(category)

        business_btn.setOnClickListener {
            category = business_btn.text.toString().toLowerCase()
            Log.d("TAG",category)
            fetchData(category)
        }

        entertainment_btn.setOnClickListener {
            category = entertainment_btn.text.toString().toLowerCase()
            Log.d("TAG",category)
            fetchData(category)
        }

        general_btn.setOnClickListener {
            category = general_btn.text.toString().toLowerCase()
            Log.d("TAG",category)
            fetchData(category)
        }

        health_btn.setOnClickListener {
            category = health_btn.text.toString().toLowerCase()
            Log.d("TAG",category)
            fetchData(category)
        }

        science_btn.setOnClickListener {
            category = science_btn.text.toString().toLowerCase()
            fetchData(category)
        }

        sports_btn.setOnClickListener {
            category = sports_btn.text.toString().toLowerCase()
            fetchData(category)
        }

        technology_btn.setOnClickListener {
            category = technology_btn.text.toString().toLowerCase()
            fetchData(category)
        }

        mAdapter = NewsListAdapter(this)
        recyclerView.adapter = mAdapter

    }

    private fun fetchData(category: String) {

        val url = "https://saurav.tech/NewsAPI/top-headlines/category/${category}/in.json"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                mAdapter.updateNews(newsArray)
            },
            {

            }
        )
        MySingleton.MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {
        val colorInt: Int = Color.parseColor("#FF0000") //red
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }

}