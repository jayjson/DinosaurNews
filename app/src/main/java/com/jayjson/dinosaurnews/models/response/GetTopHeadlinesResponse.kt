package com.jayjson.dinosaurnews.models.response

import com.jayjson.dinosaurnews.models.Article
import com.squareup.moshi.Json

data class GetTopHeadlinesResponse(@field:Json(name = "articles") val articles: List<Article> = listOf())
