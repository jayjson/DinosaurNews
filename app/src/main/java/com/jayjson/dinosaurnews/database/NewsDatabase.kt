package com.jayjson.dinosaurnews.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jayjson.dinosaurnews.database.dao.ArticleDao
import com.jayjson.dinosaurnews.database.dao.SourceDao
import com.jayjson.dinosaurnews.model.Article
import com.jayjson.dinosaurnews.model.Source
import com.jayjson.dinosaurnews.model.typeConverter.SourceTypeConverter

const val DATABASE_VERSION = 1

@Database(
    entities = [Article::class, Source::class],
    version = DATABASE_VERSION
)
@TypeConverters(SourceTypeConverter::class)

abstract class NewsDatabase: RoomDatabase() {
    companion object {
        private const val DATABASE_NAME = "News"

        fun buildDatabase(context: Context): NewsDatabase {
            return Room.databaseBuilder(
                context,
                NewsDatabase::class.java,
                DATABASE_NAME
            )
                .allowMainThreadQueries()
                .build()
        }
    }

    abstract fun articleDao(): ArticleDao

    abstract fun sourceDao(): SourceDao
}