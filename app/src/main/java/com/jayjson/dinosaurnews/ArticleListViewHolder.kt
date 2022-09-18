package com.jayjson.dinosaurnews

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ArticleListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleTextView = itemView.findViewById<TextView>(R.id.textView_title)
}