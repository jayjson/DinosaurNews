package com.jayjson.dinosaurnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.view.children
import com.jayjson.dinosaurnews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val service = NewService()

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
        val mainGroup = binding.mainGroup
        val articles = service.getArticles()

        var index = 0;
        for (childView in mainGroup.children) {
            if (childView is TextView) {
                val article = articles[index]
                childView.text = prepareDisplayText(article.title, article.author, article.source.name)
                index++
            }
        }
    }

    private fun prepareDisplayText(title: String, author: String? = null, sourceName: String): String {
        var textToDisplay = "$title"
        if (author != null) {
            textToDisplay += " by $author"
        }
        textToDisplay += " ($sourceName)"
        return textToDisplay
    }
}