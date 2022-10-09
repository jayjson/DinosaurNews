package com.jayjson.dinosaurnews.repository

import android.util.Log
import com.jayjson.dinosaurnews.database.dao.ArticleDao
import com.jayjson.dinosaurnews.database.dao.SourceDao
import com.jayjson.dinosaurnews.model.*
import com.jayjson.dinosaurnews.networking.RemoteApi
import com.jayjson.dinosaurnews.model.Success
import com.jayjson.dinosaurnews.model.Failure
import kotlinx.coroutines.Dispatchers

class NewsRepositoryImp(
    private val articleDao: ArticleDao,
    private val sourceDao: SourceDao,
    private val remoteApi: RemoteApi
) : NewsRepository {
    override suspend fun getArticles(): List<Article> {
        val articlesFromLocalDb = articleDao.getArticles()
        Log.i(TAG, "articlesFromLocalDb size = ${articlesFromLocalDb.size}")

        try {
            val articlesFromNetworkResult = remoteApi.getTopHeadlines()
            when (articlesFromNetworkResult) {
                is Success -> {
                    val fetchedArticles = articlesFromNetworkResult.data
                    articleDao.clearArticles()
                    articleDao.addArticles(fetchedArticles)

                    val fetchedSources = fetchedArticles.map { it.source }.distinct()
                    sourceDao.addSources(fetchedSources)

                    return articlesFromNetworkResult.data
                }
                is Failure -> {
                    Log.e(TAG, "Fetching articles failed")
                    return articlesFromLocalDb
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
            return articlesFromLocalDb
        }
    }

    override suspend fun addArticles(articles: List<Article>) {
        articleDao.addArticles(articles)
    }

    override suspend fun clearArticles() {
        articleDao.clearArticles()
    }

    override suspend fun getSources(): List<Source> {
        return sourceDao.getSources()
    }

    override suspend fun addSources(sources: List<Source>) {
        sourceDao.addSources(sources)
    }

    companion object {
        private const val TAG = "NewsRepositoryImp"
    }
}