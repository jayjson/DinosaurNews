package com.jayjson.dinosaurnews

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.jayjson.dinosaurnews.database.NewsDatabase
import com.jayjson.dinosaurnews.model.Country
import com.jayjson.dinosaurnews.networking.NetworkStatusChecker
import com.jayjson.dinosaurnews.networking.RemoteApi
import com.jayjson.dinosaurnews.networking.buildApiService
import com.jayjson.dinosaurnews.prefsstore.PrefsStore
import com.jayjson.dinosaurnews.prefsstore.PrefsStoreImp
import com.jayjson.dinosaurnews.repository.NewsRepository
import com.jayjson.dinosaurnews.repository.NewsRepositoryImp

private const val KEY_PREFERENCES = "dinosaur_news_preferences"
private const val KEY_TOKEN = "token"

const val PREFS_NAME = "dinosaur_news_preferences"

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
                remoteApi,
                networkStatusChecker,
                prefsStore
            )
        }

        private val preferences by lazy {
            instance.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE)
        }

        val prefsStore: PrefsStore by lazy {
            PrefsStoreImp(instance)
        }

        private val networkStatusChecker by lazy {
            NetworkStatusChecker(instance.getSystemService(ConnectivityManager::class.java))
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