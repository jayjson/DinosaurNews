package com.jayjson.dinosaurnews.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val source: Source,
    val author: String? = null,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String? = null,
    val publishedAt: String,
    val content: String
) : Serializable