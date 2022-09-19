package com.jayjson.dinosaurnews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jayjson.dinosaurnews.databinding.ActivityMainBinding
import com.jayjson.dinosaurnews.models.Article

import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import com.jayjson.dinosaurnews.models.Country
import com.jayjson.dinosaurnews.networking.NetworkStatusChecker

@RequiresApi(Build.VERSION_CODES.M)
class MainActivity : AppCompatActivity(), ArticleListAdapter.ArticleClickListener {
    private lateinit var binding: ActivityMainBinding
    private val service: NewsService = InMemoryNewsServiceImpl()
    private val remoteApi = App.remoteApi

    private val networkStatusChecker by lazy {
        NetworkStatusChecker(getSystemService(ConnectivityManager::class.java))
    }

    private lateinit var articleListRecyclerView: RecyclerView

    companion object {
        const val INTENT_ARTICLE_KEY = "article"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        fetchTopHeadlines()
    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun articleClicked(article: Article) {
        showArticleDetails(article)
    }

    private fun showArticleDetails(article: Article) {
        val articleDetail = Intent(this, ArticleDetailActivity::class.java)
        articleDetail.putExtra(INTENT_ARTICLE_KEY, article)
        startActivity(articleDetail)
    }

    private fun fetchTopHeadlines() {
        val country = Country.US

        networkStatusChecker.performIfConnectedToInternet {
            remoteApi.getTopHeadlines(country) { articles: List<Article>, throwable: Throwable? ->
                if (throwable != null || articles.isEmpty()) {
                    showLoginError()
                } else if (throwable != null) {
                    populateArticles(articles)
                }
            }
        }
    }

    private fun showLoginError() {
        Snackbar.make(binding.root, "Could not fetch articles :(", Snackbar.LENGTH_LONG).show()
    }

    private fun populateArticles(articles: List<Article>) {
        articleListRecyclerView = binding.articleListRecyclerview
        articleListRecyclerView.layoutManager = LinearLayoutManager(this)
        articleListRecyclerView.adapter = ArticleListAdapter(articles, this)
    }
}
