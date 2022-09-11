package com.jayjson.dinosaurnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jayjson.dinosaurnews.models.Article

class ArticleDetailActivity : AppCompatActivity() {
//    private var article: Article? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)
//        article = intent.getSerializableExtra(MainActivity.INTENT_ARTICLE_KEY) as? Article
//        title = article?.author
    }
}