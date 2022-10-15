package com.jayjson.dinosaurnews.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "sources")
data class Source(
//    var id: String = UUID.randomUUID().toString(), // android.database.sqlite.SQLiteConstraintException: NOT NULL constraint failed: sources.id (code 1299 SQLITE_CONSTRAINT_NOTNULL)
    @PrimaryKey
    val name: String,
    val description: String? = null,
    val url: String? = null,
    val category: SourceCategory? = null,
    val language: Language? = null,
    val country: Country? = null
) : Serializable