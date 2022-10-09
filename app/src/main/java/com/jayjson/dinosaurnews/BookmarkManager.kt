package com.jayjson.dinosaurnews

import android.content.Context
import androidx.preference.PreferenceManager
import com.google.gson.GsonBuilder
import com.jayjson.dinosaurnews.model.Article

class BookmarkManager(private val context: Context) {
    fun save(article: Article) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context).edit()
        val jsonString = GsonBuilder().create().toJson(article)
        sharedPrefs.putString(article.title, jsonString)
        sharedPrefs.apply()
        println("article saved: ${article.title}")
    }

    // Todo: To be implemented later when UI becomes ready
//    fun readLists(): ArrayList<Article> { }
}