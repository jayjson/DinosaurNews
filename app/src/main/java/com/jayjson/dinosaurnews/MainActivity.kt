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
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import com.jayjson.dinosaurnews.models.Country
import com.jayjson.dinosaurnews.models.OperationState
import com.jayjson.dinosaurnews.networking.NetworkStatusChecker

@RequiresApi(Build.VERSION_CODES.M)
class MainActivity : AppCompatActivity(), ArticleListAdapter.ArticleClickListener {
    private var state = OperationState.ready
        set(newValue) {
            field = newValue
            resetUI(newValue)
        }

    private lateinit var binding: ActivityMainBinding
    private val remoteApi = App.remoteApi

    private val networkStatusChecker by lazy {
        NetworkStatusChecker(getSystemService(ConnectivityManager::class.java))
    }

    private lateinit var articleListRecyclerView: RecyclerView
    private lateinit var loadingIndicator: ProgressBar
    private lateinit var errorStateView: LinearLayout
    private lateinit var tryAgainButton: Button

    companion object {
        const val INTENT_ARTICLE_KEY = "article"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupSubviews()
        fetchTopHeadlines()
    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupSubviews() {
        title = "Top Headlines"
        articleListRecyclerView = binding.articleListRecyclerview
        loadingIndicator = binding.progressIndicator
        errorStateView = binding.linearLayoutErrorState
        tryAgainButton = binding.buttonErrorState

        tryAgainButton.setOnClickListener {
            fetchTopHeadlines()
        }
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
        state = OperationState.loading
        val country = Country.US

        networkStatusChecker.performIfConnectedToInternet {
            remoteApi.getTopHeadlines(country) { articles: List<Article>, throwable: Throwable? ->
                if (throwable != null || articles.isEmpty()) {
                    state = OperationState.failed
                } else if (articles.isNotEmpty()) {
                    populateArticles(articles)
                    state = OperationState.succeeded
                }
            }
        }
    }

    private fun resetUI(newState: OperationState) {
        when (newState) {
            OperationState.ready -> {
                articleListRecyclerView.visibility = View.INVISIBLE
                loadingIndicator.visibility = View.INVISIBLE
                errorStateView.visibility = View.INVISIBLE
            }
            OperationState.loading -> {
                articleListRecyclerView.visibility = View.INVISIBLE
                loadingIndicator.visibility = View.VISIBLE
                errorStateView.visibility = View.INVISIBLE
            }
            OperationState.succeeded -> {
                articleListRecyclerView.visibility = View.VISIBLE
                loadingIndicator.visibility = View.INVISIBLE
                errorStateView.visibility = View.INVISIBLE
            }
            OperationState.failed -> {
                articleListRecyclerView.visibility = View.INVISIBLE
                loadingIndicator.visibility = View.INVISIBLE
                errorStateView.visibility = View.VISIBLE
            }
        }
    }

    private fun populateArticles(articles: List<Article>) {
        articleListRecyclerView.layoutManager = LinearLayoutManager(this)
        articleListRecyclerView.adapter = ArticleListAdapter(articles, this)
    }
}
