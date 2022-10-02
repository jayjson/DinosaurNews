package com.jayjson.dinosaurnews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jayjson.dinosaurnews.model.Article

class ArticleListAdapter(var articles: List<Article>, val clickListener: ArticleClickListener) : RecyclerView.Adapter<ArticleListViewHolder>() {

    interface ArticleClickListener {
        fun articleClicked(article: Article)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleListViewHolder {
        val view = LayoutInflater.from(parent?.context)
            .inflate(R.layout.article_view_holder, parent, false)
        return ArticleListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleListViewHolder, position: Int) {
        val article = articles[position]
        holder.titleTextView.text = article.title
        holder.itemView.setOnClickListener {
            clickListener.articleClicked(article)
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }
}