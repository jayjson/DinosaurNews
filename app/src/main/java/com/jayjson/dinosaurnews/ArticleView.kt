package com.jayjson.dinosaurnews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.jayjson.dinosaurnews.databinding.ArticleViewBinding
import com.jayjson.dinosaurnews.models.Article

class ArticleView(
    context: Context,
    attrs: AttributeSet? = null
): ConstraintLayout(context, attrs) {
    private val binding = ArticleViewBinding.inflate(LayoutInflater.from(context), this)

    fun setData(article: Article) {
        binding.titleTextView.text = article.title
        val authorName = if (article.author != null) article.author else "unknown author"
        binding.authorTextView.text =  "by ${authorName}"
    }
}