package com.example.beerapp.data

import android.content.Context
import androidx.room.Room
import com.example.beerapp.data.source.local.db.AppDatabase
import com.example.beerapp.data.source.local.db.beer.BeerDao
import com.example.beerapp.data.source.local.db.currentitemindex.CurrentItemDao
import com.example.beerapp.data.source.remote.network.BeersApiService
import com.example.beerapp.data.source.remote.network.RetrofitProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context, AppDatabase::class.java, "database",
        ).build()

    @Provides
    fun provideBeerDao(appDatabase: AppDatabase): BeerDao =
        appDatabase.beerDao()

    @Provides
    fun provideCurrentItemDao(appDatabase: AppDatabase): CurrentItemDao =
        appDatabase.currentItemDao()

    @Provides
    fun provideBeerApiService(): BeersApiService =
        RetrofitProvider.instance.create(BeersApiService::class.java)
}