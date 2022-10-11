package com.jayjson.dinosaurnews.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.asLiveData
import com.jayjson.dinosaurnews.database.dao.ArticleDao
import com.jayjson.dinosaurnews.database.dao.SourceDao
import com.jayjson.dinosaurnews.model.*
import com.jayjson.dinosaurnews.networking.NetworkStatusChecker
import com.jayjson.dinosaurnews.networking.RemoteApi
import com.jayjson.dinosaurnews.prefsstore.PrefsStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepositoryImp(
    private val articleDao: ArticleDao,
    private val sourceDao: SourceDao,
    private val remoteApi: RemoteApi,
    private val networkStatusChecker: NetworkStatusChecker,
    private val prefsStore: PrefsStore
) : NewsRepository {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun getArticles(): Flow<Result<List<Article>>> {
        return flow {
            val articlesFromLocalDb = articleDao.getArticles()
            Log.i(TAG, "articlesFromLocalDb size = ${articlesFromLocalDb.size}")

            emit(Success(articlesFromLocalDb))

            try {
                val isWiFiOnlyApplicable = prefsStore.shouldUseWiFiOnly().asLiveData().value
                if ((isWiFiOnlyApplicable != true) || (isWiFiOnlyApplicable == true && networkStatusChecker.hasWifiConnection())) {
                    val articlesFromNetworkResult = remoteApi.getTopHeadlines()
                    when (articlesFromNetworkResult) {
                        is Success -> {
                            val fetchedArticles = articlesFromNetworkResult.data
                            articleDao.clearArticles()
                            articleDao.addArticles(fetchedArticles)

//                        val fetchedSources = fetchedArticles.map { it.source }.distinct()
//                        sourceDao.addSources(fetchedSources)

                            emit(Success(fetchedArticles))
                        }
                        is Failure -> {
                            Log.e(TAG, "Fetching articles failed")
                            emit(Success(articlesFromLocalDb))
                        }
                    }
                }
            } catch (error: Exception) {
                Log.e(TAG, error.toString())
                emit(Failure(error))
            }
        }
    }

    override fun addArticles(articles: List<Article>) {
        articleDao.addArticles(articles)
    }

    override fun clearArticles() {
        articleDao.clearArticles()
    }

    override fun searchArticles(search: String): List<Article> {
        return articleDao.searchArticles(search)
    }

    override fun getSources(): List<Source> {
        return sourceDao.getSources()
    }

    override fun addSources(sources: List<Source>) {
        sourceDao.addSources(sources)
    }

    companion object {
        private const val TAG = "NewsRepositoryImp"
    }
}