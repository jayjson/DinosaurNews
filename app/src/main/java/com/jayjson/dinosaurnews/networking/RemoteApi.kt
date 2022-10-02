package com.jayjson.dinosaurnews.networking

import com.jayjson.dinosaurnews.model.Article
import com.jayjson.dinosaurnews.model.Country
import com.jayjson.dinosaurnews.model.Result
import com.jayjson.dinosaurnews.model.Failure
import com.jayjson.dinosaurnews.model.Success

const val BASE_URL = "https://newsapi.org"
const val API_KEY = "7939deb24a904014a7ec5858c004a1f9"

class RemoteApi(private val apiService: RemoteApiService) {
    suspend fun getTopHeadlines(country: Country): Result<List<Article>> = try {
        val data = apiService.getTopHeadlines(API_KEY, country.name)
        Success(data.articles)
    } catch (error: Throwable) {
        Failure(error)
    }
}