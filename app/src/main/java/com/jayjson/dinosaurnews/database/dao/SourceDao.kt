package com.jayjson.dinosaurnews.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jayjson.dinosaurnews.model.Source

@Dao
interface SourceDao {
    @Query("SELECT * FROM sources")
    suspend fun getSources(): List<Source>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSources(sources: List<Source>)
}
