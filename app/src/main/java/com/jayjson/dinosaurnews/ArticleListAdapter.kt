package com.jayjson.dinosaurnews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jayjson.dinosaurnews.models.Article

class ArticleListAdapter(var articles: List<Article>) : RecyclerView.Adapter<ArticleListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleListViewHolder {
        val view = LayoutInflater.from(parent?.context)
            .inflate(R.layout.article_view_holder, parent, false)
        return ArticleListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleListViewHolder, position: Int) {
        holder.titleTextView.text = articles[position].title
    }

    override fun getItemCount(): Int {
        return articles.size
    }
}