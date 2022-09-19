package com.jayjson.dinosaurnews.networking

import com.jayjson.dinosaurnews.models.response.GetTopHeadlinesResponse
import retrofit2.Call
import retrofit2.http.*

interface RemoteApiService {

    @GET("/v2/top-headlines")
    fun getTopHeadlines(@Header("Authorization") token: String): Call<GetTopHeadlinesResponse>
}