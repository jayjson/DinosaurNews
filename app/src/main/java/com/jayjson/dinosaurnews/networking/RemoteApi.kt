package com.jayjson.dinosaurnews.networking

import com.jayjson.dinosaurnews.models.Article
import com.jayjson.dinosaurnews.models.Country
import com.jayjson.dinosaurnews.models.Result
import com.jayjson.dinosaurnews.models.Failure
import com.jayjson.dinosaurnews.models.Success
import com.jayjson.dinosaurnews.models.response.GetTopHeadlinesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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