package com.jayjson.dinosaurnews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jayjson.dinosaurnews.databinding.ActivityMainBinding
import com.jayjson.dinosaurnews.models.Article

class MainActivity : AppCompatActivity(), ArticleListAdapter.ArticleClickListener {
    private lateinit var binding: ActivityMainBinding
    private val service: NewsService = InMemoryNewsServiceImpl()

    private lateinit var articleListRecyclerView: RecyclerView

    companion object {
        const val INTENT_ARTICLE_KEY = "article"
    }

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
        articleListRecyclerView = binding.articleListRecyclerview
        articleListRecyclerView.layoutManager = LinearLayoutManager(this)
        articleListRecyclerView.adapter = ArticleListAdapter(articles, this)
    }

    override fun articleClicked(article: Article) {
        showArticleDetails(article)
    }

    private fun showArticleDetails(article: Article) {
        println("showArticleDetails")

//        val article = Intent(this, ArticleDetailActivity::class.java)
//        article.putExtra(INTENT_ARTICLE_KEY, article)
//        startActivity(article)
    }
}
