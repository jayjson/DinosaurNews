package com.jayjson.dinosaurnews

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jayjson.dinosaurnews.databinding.ActivityMainBinding
import com.jayjson.dinosaurnews.model.Article
import com.jayjson.dinosaurnews.model.Failure
import com.jayjson.dinosaurnews.model.OperationState
import com.jayjson.dinosaurnews.model.Success
import com.jayjson.dinosaurnews.viewmodel.ArticlesListViewModel

@RequiresApi(Build.VERSION_CODES.M)
class MainActivity : AppCompatActivity(), ArticleListAdapter.ArticleClickListener {
    private var state = OperationState.ready
        set(newValue) {
            field = newValue
            resetUI(newValue)
        }

    private lateinit var binding: ActivityMainBinding

    private val viewModel: ArticlesListViewModel by viewModels {
        ArticlesListViewModel.Factory(
            newsRepo = App.repository,
            prefsStore = App.prefsStore
        )
    }

    private lateinit var wifiOnlyCheckbox: CheckBox
    private lateinit var articleListRecyclerView: RecyclerView
    private lateinit var loadingIndicator: ProgressBar
    private lateinit var errorStateView: LinearLayout
    private lateinit var tryAgainButton: Button
    private lateinit var swipeContainer: SwipeRefreshLayout

    companion object {
        const val INTENT_ARTICLE_KEY = "article"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupSubviews()
        setupObservers()
    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupSubviews() {
        title = "Top Headlines"
        wifiOnlyCheckbox = binding.checkBoxWifiOnly
        articleListRecyclerView = binding.articleListRecyclerview
        loadingIndicator = binding.progressIndicator
        errorStateView = binding.linearLayoutErrorState
        tryAgainButton = binding.buttonErrorState
        swipeContainer = binding.swipeContainer

        wifiOnlyCheckbox.setOnClickListener {
            viewModel.toggleWiFiOnly()
        }

        tryAgainButton.setOnClickListener {
            setupObservers()
        }

        swipeContainer.setOnRefreshListener {
            setupObservers()
            swipeContainer.isRefreshing = false
        }

        val queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // do nothing, search is done on text changes
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { searchQuery ->
                    viewModel.searchArticles(searchQuery)
                }
                return true
            }
        }

        binding.searchView.setOnQueryTextListener(queryTextListener)
    }

    override fun articleClicked(article: Article) {
        showArticleDetails(article)
    }

    private fun showArticleDetails(article: Article) {
        val articleDetail = Intent(this, ArticleDetailActivity::class.java)
        articleDetail.putExtra(INTENT_ARTICLE_KEY, article)
        startActivity(articleDetail)
    }

    private fun setupObservers() {
        viewModel.articles.observe(this) { articlesResult ->
            when (articlesResult) {
                is Success -> {
                    populateArticles(articlesResult.data)
                    state = OperationState.succeeded
                }
                is Failure -> {
                    state = OperationState.failed
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
