package com.jayjson.dinosaurnews.repository

import com.jayjson.dinosaurnews.database.dao.ArticleDao
import com.jayjson.dinosaurnews.database.dao.SourceDao
import com.jayjson.dinosaurnews.networking.RemoteApi
import com.jayjson.dinosaurnews.networking.RemoteApiService

class NewsRepositoryImp(
    private val articleDao: ArticleDao,
    private val sourceDao: SourceDao,
    private val remoteApi: RemoteApi
) : NewsRepository {
}