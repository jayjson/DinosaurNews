package com.jayjson.dinosaurnews

import android.app.Application
import android.content.Context
import com.jayjson.dinosaurnews.database.NewsDatabase
import com.jayjson.dinosaurnews.model.Country
import com.jayjson.dinosaurnews.networking.RemoteApi
import com.jayjson.dinosaurnews.networking.buildApiService
import com.jayjson.dinosaurnews.repository.NewsRepository
import com.jayjson.dinosaurnews.repository.NewsRepositoryImp

private const val KEY_PREFERENCES = "dinosaur_news_preferences"
private const val KEY_TOKEN = "token"

class App : Application() {

    companion object {
        private lateinit var instance: App

        private val database: NewsDatabase by lazy {
            NewsDatabase.buildDatabase(instance)
        }

        val repository: NewsRepository by lazy {
            NewsRepositoryImp(
                database.articleDao(),
                database.sourceDao(),
                remoteApi
            )
        }

        private val preferences by lazy {
            instance.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE)
        }

        fun saveToken(token: String) {
            preferences.edit()
                .putString(KEY_TOKEN, token)
                .apply()
        }

        fun getToken() = preferences.getString(KEY_TOKEN, "") ?: ""

        private val apiService by lazy { buildApiService() }

        val remoteApi by lazy { RemoteApi(apiService) }

        val defaultCountry = Country.US
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}