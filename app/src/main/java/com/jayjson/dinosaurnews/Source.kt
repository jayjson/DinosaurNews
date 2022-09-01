package com.jayjson.dinosaurnews

data class Source(val id: String? = null,
                  val name: String,
                  val description: String? = null,
                  val url: String? = null,
                  val category: String? = null,
                  val language: String? = null,
                  val country: String? = null)