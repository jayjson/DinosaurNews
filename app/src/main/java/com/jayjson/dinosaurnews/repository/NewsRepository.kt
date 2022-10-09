package com.jayjson.dinosaurnews.repository

import com.jayjson.dinosaurnews.model.Article
import com.jayjson.dinosaurnews.model.Result
import com.jayjson.dinosaurnews.model.Source

interface NewsRepository {
    suspend fun getArticles(): Result<List<Article>>
    suspend fun addArticles(articles: List<Article>)
    suspend fun clearArticles()
    suspend fun getSources(): List<Source>
    suspend fun addSources(sources: List<Source>)
}