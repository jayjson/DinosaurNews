package com.jayjson.dinosaurnews.di

import com.jayjson.dinosaurnews.prefsstore.PrefsStore
import com.jayjson.dinosaurnews.prefsstore.PrefsStoreImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class StoreModule {

    @Binds
    abstract fun bindPrefsStore(prefsStoreImpl: PrefsStoreImp): PrefsStore
}