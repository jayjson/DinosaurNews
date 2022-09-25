package com.jayjson.dinosaurnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.jayjson.dinosaurnews.databinding.ActivityArticleDetailBinding
import com.jayjson.dinosaurnews.model.Article

class ArticleDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleDetailBinding
    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var bookmarkButton: Button

    lateinit var article: Article

    private val bookmarkManager: BookmarkManager = BookmarkManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBindingAndViews()
        populateArticleDetails()
    }

    private fun setupBindingAndViews() {
        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        titleTextView = binding.titleTextView
        descriptionTextView = binding.descriptionTextView

        bookmarkButton = binding.bookmarkButton
        bookmarkButton.setOnClickListener { view ->
            bookmarkManager.save(article)
            Snackbar.make(view, "Article Bookmarked!", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun populateArticleDetails() {
        val tempArticle = intent.getSerializableExtra(MainActivity.INTENT_ARTICLE_KEY) as? Article
        article = tempArticle!!
        title = if (article.author != null) article.author else "Unknown author"
        titleTextView.text = article.title
        descriptionTextView.text = article.description
    }
}