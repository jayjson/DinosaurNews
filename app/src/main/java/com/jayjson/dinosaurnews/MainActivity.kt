package com.jayjson.dinosaurnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jayjson.dinosaurnews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val service: NewsService = InMemoryNewsServiceImpl()

    private lateinit var articleListRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        populateArticles()
    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun populateArticles() {
        val articles = service.getArticles()

        articleListRecyclerView = findViewById(R.id.article_list_recyclerview)
        articleListRecyclerView.layoutManager = LinearLayoutManager(this)
        articleListRecyclerView.adapter = ArticleListAdapter(articles)

//        articles.forEach { article ->
//            val articleView = ArticleView(this)
//            articleView.setData(article)
//            binding.articlesContainer.addView(articleView)
//        }
    }
}