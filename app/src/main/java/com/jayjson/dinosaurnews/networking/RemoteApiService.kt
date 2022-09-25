package com.jayjson.dinosaurnews.networking

import com.jayjson.dinosaurnews.models.response.GetTopHeadlinesResponse
import retrofit2.Call
import retrofit2.http.*

interface RemoteApiService {

    @GET("/v2/top-headlines")
    suspend fun getTopHeadlines(@Query("apiKey") apiKey: String,
                        @Query("country") country: String): GetTopHeadlinesResponse
}