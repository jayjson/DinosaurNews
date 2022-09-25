package com.jayjson.dinosaurnews

import com.jayjson.dinosaurnews.model.Country
import com.jayjson.dinosaurnews.model.Language
import com.jayjson.dinosaurnews.model.SourceCategory
import java.io.Serializable

data class Source(val id: String? = null,
                  val name: String,
                  val description: String? = null,
                  val url: String? = null,
                  val category: SourceCategory? = null,
                  val language: Language? = null,
                  val country: Country? = null) : Serializable