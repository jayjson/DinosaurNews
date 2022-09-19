package com.jayjson.dinosaurnews.networking

import com.jayjson.dinosaurnews.App
import com.jayjson.dinosaurnews.models.Article
import com.jayjson.dinosaurnews.models.response.GetTopHeadlinesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val BASE_URL = "https://newsapi.org"

class RemoteApi(private val apiService: RemoteApiService) {

    fun getTopHeadlines(onTasksReceived: (List<Article>, Throwable?) -> Unit) {
        apiService.getTopHeadlines(App.getToken()).enqueue(object : Callback<GetTopHeadlinesResponse> {
            override fun onFailure(call: Call<GetTopHeadlinesResponse>, error: Throwable) {
                onTasksReceived(emptyList(), error)
            }

            override fun onResponse(call: Call<GetTopHeadlinesResponse>, response: Response<GetTopHeadlinesResponse>) {
                val data = response.body()

                if (data != null && data.articles.isNotEmpty()) {
                    onTasksReceived(data.articles, null)
                } else {
                    onTasksReceived(emptyList(), NullPointerException("No data available!"))
                }
            }
        })
    }
}