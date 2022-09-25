package com.jayjson.dinosaurnews.networking

import com.jayjson.dinosaurnews.models.Article
import com.jayjson.dinosaurnews.models.Country
import com.jayjson.dinosaurnews.models.response.GetTopHeadlinesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val BASE_URL = "https://newsapi.org"
const val API_KEY = "7939deb24a904014a7ec5858c004a1f9"

class RemoteApi(private val apiService: RemoteApiService) {

    fun getTopHeadlines(country: Country, onArticlesReceived: (List<Article>, Throwable?) -> Unit) {
        apiService.getTopHeadlines(API_KEY, country.name).enqueue(object : Callback<GetTopHeadlinesResponse> {
            override fun onFailure(call: Call<GetTopHeadlinesResponse>, error: Throwable) {
                onArticlesReceived(emptyList(), error)
            }

            override fun onResponse(call: Call<GetTopHeadlinesResponse>, response: Response<GetTopHeadlinesResponse>) {
                val data = response.body()

                if (data != null && data.articles.isNotEmpty()) {
                    onArticlesReceived(data.articles, null)
                } else {
                    onArticlesReceived(emptyList(), NullPointerException("No data available!"))
                }
            }
        })
    }
}