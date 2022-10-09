package com.jayjson.dinosaurnews.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "sources")
data class Source(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String? = null,
    val url: String? = null,
    val category: SourceCategory? = null,
    val language: Language? = null,
    val country: Country? = null
) : Serializable