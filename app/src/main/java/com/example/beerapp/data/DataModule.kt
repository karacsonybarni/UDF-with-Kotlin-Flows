package com.example.beerapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.beerapp.data.source.local.db.AppDatabase
import com.example.beerapp.data.source.local.db.beer.BeerDao
import com.example.beerapp.data.source.remote.network.BeersApiService
import com.example.beerapp.data.source.remote.network.RetrofitProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    private val Context.dataStore: DataStore<Preferences>
            by preferencesDataStore("current_item")

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context, AppDatabase::class.java, "database",
        ).build()

    @Provides
    fun provideBeerDao(appDatabase: AppDatabase): BeerDao =
        appDatabase.beerDao()

    @Provides
    fun provideBeerApiService(): BeersApiService =
        RetrofitProvider.instance.create(BeersApiService::class.java)

    @Singleton
    @Provides
    fun provideCurrentItemDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.dataStore
}