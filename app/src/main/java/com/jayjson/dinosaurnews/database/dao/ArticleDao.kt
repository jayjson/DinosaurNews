package com.jayjson.dinosaurnews.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jayjson.dinosaurnews.model.Article

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles")
    suspend fun getArticles(): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addArticles(articles: List<Article>)

    @Query("DELETE FROM articles")
    suspend fun clearArticles()
}
