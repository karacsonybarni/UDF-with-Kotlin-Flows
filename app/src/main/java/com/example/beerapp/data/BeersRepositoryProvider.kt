package com.example.beerapp.data

import android.content.Context
import androidx.room.Room
import com.example.beerapp.data.source.local.BeersLocalDataSource
import com.example.beerapp.data.source.local.CurrentItemLocalDataSource
import com.example.beerapp.data.source.local.db.AppDatabase
import com.example.beerapp.data.source.remote.BeersRemoteDataSource
import com.example.beerapp.data.source.remote.network.BeersApiService
import com.example.beerapp.data.source.remote.network.RetrofitProvider

object BeersRepositoryProvider {

    private var applicationContext: Context? = null

    val beersRepository: BeersRepository by lazy {
        assert(applicationContext != null)
        val database = buildDatabase()
        val beersRemoteDataSource = BeersRemoteDataSource(createApiService())
        val beersLocalDataSource = BeersLocalDataSource(database.beerDao())
        val currentItemLocalDataSource = CurrentItemLocalDataSource(database.currentItemDao())
        applicationContext = null
        BeersRepository(beersRemoteDataSource, beersLocalDataSource, currentItemLocalDataSource)
    }

    fun init(applicationContext: Context) {
        this.applicationContext = applicationContext
    }

    private fun createApiService(): BeersApiService =
        RetrofitProvider.instance.create(BeersApiService::class.java)

    private fun buildDatabase(): AppDatabase =
        Room.databaseBuilder(
            applicationContext!!,
            AppDatabase::class.java, "database",
        ).build()
}