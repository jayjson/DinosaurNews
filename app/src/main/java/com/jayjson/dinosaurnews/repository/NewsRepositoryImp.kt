package com.jayjson.dinosaurnews.repository

import android.util.Log
import com.jayjson.dinosaurnews.database.dao.ArticleDao
import com.jayjson.dinosaurnews.database.dao.SourceDao
import com.jayjson.dinosaurnews.model.*
import com.jayjson.dinosaurnews.networking.RemoteApi
import com.jayjson.dinosaurnews.model.Success
import com.jayjson.dinosaurnews.model.Failure

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
                    articleDao.addArticles(articlesFromNetworkResult.data)
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
        TODO("Not yet implemented")
    }

    override suspend fun clearArticles() {
        TODO("Not yet implemented")
    }

    override suspend fun getSources(): List<Source> {
        TODO("Not yet implemented")
    }

    override suspend fun addSources(articles: List<Source>) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val TAG = "NewsRepositoryImp"
    }
}