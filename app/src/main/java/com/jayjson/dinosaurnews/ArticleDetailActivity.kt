package com.jayjson.dinosaurnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jayjson.dinosaurnews.databinding.ActivityArticleDetailBinding
import com.jayjson.dinosaurnews.databinding.ActivityMainBinding
import com.jayjson.dinosaurnews.models.Article
import org.w3c.dom.Text

class ArticleDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleDetailBinding
    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView

    lateinit var article: Article

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
    }

    private fun populateArticleDetails() {
        val tempArticle = intent.getSerializableExtra(MainActivity.INTENT_ARTICLE_KEY) as? Article
        article = tempArticle!!
        title = if (article.author != null) article.author else "Unknown author"
        titleTextView.text = article.title
        descriptionTextView.text = article.description
    }
}