package com.jayjson.dinosaurnews.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "articles")
data class Article(
//    val id: String = UUID.randomUUID().toString(), // Commented out due to this error: android.database.sqlite.SQLiteConstraintException: NOT NULL constraint failed: articles.id (code 1299 SQLITE_CONSTRAINT_NOTNULL)
    val source: Source,
    val author: String? = null,
    @PrimaryKey
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String? = null,
    val publishedAt: String,
    val content: String?
) : Serializable