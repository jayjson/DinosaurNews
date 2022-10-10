package com.jayjson.dinosaurnews.prefsstore

import kotlinx.coroutines.flow.Flow

interface PrefsStore {
    fun shouldUseWiFiOnly(): Flow<Boolean>

    suspend fun toggleUseWiFiOnly()
}