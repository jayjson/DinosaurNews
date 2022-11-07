package com.jayjson.dinosaurnews.prefsstore

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.jayjson.dinosaurnews.PREFS_NAME
import com.jayjson.dinosaurnews.repository.NewsRepositoryImp
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private const val STORE_NAME = "dinosaur_news_data_store"
class PrefsStoreImp @Inject constructor(
    @ApplicationContext context: Context
): PrefsStore {

    private val dataStore = context.createDataStore(
        name = STORE_NAME,
        migrations = listOf(SharedPreferencesMigration(context, PREFS_NAME))
    )

    override fun shouldUseWiFiOnly() = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            Log.e(TAG, exception.toString())
            throw exception
        }
    }.map { it[PreferenceKeys.USE_WIFI_ONLY] ?: false }

    override suspend fun toggleUseWiFiOnly() {
        dataStore.edit {
            it[PreferenceKeys.USE_WIFI_ONLY] = !(it[PreferenceKeys.USE_WIFI_ONLY] ?: false)
        }
    }

    private object PreferenceKeys {
        val USE_WIFI_ONLY = preferencesKey<Boolean>("wifi_check_enabled")
    }

    companion object {
        private const val TAG = "PrefsStoreImp"
    }
}