package com.jayjson.dinosaurnews.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jayjson.dinosaurnews.model.Article

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles")
    fun getArticles(): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addArticles(articles: List<Article>)

    @Query("DELETE FROM articles")
    fun clearArticles()

    @Query("SELECT * FROM articles WHERE title LIKE :search")
    fun searchArticles(search: String): List<Article>
}
