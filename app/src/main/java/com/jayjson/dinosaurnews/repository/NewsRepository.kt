package com.jayjson.dinosaurnews.repository

import com.jayjson.dinosaurnews.model.Article
import com.jayjson.dinosaurnews.model.Result
import com.jayjson.dinosaurnews.model.Source
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getArticles(shouldFetchFromNetwork: Boolean): Flow<Result<List<Article>>>
    fun addArticles(articles: List<Article>)
    fun clearArticles()
    fun searchArticles(search: String): List<Article>
    fun getSources(): List<Source>
    fun addSources(sources: List<Source>)
}