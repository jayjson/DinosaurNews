package com.jayjson.dinosaurnews.models.request

import com.jayjson.dinosaurnews.models.Country
import com.squareup.moshi.Json

class GetTopHeadlinesRequest(
    @field:Json(name = "apiKey") val apiKey: String,
    @field:Json(name = "country") val country: Country)