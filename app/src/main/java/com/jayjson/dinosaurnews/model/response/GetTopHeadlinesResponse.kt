package com.jayjson.dinosaurnews.model.response

import com.jayjson.dinosaurnews.model.Article
import com.squareup.moshi.Json

data class GetTopHeadlinesResponse(@field:Json(name = "articles") val articles: List<Article> = listOf())
