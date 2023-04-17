package com.example.beerapp.data

import android.content.Context
import androidx.room.Room
import com.example.beerapp.data.source.local.BeersLocalDataSource
import com.example.beerapp.data.source.local.db.AppDatabase
import com.example.beerapp.data.source.local.db.BeerDao
import com.example.beerapp.data.source.remote.BeersRemoteDataSource
import com.example.beerapp.data.source.remote.network.BeersApiService
import com.example.beerapp.data.source.remote.network.RetrofitProvider

object BeersRepositoryProvider {

    private var applicationContext: Context? = null

    private val apiService: BeersApiService
        get() = RetrofitProvider.instance.create(BeersApiService::class.java)

    private val dao: BeerDao
        get() {
            val database = Room.databaseBuilder(
                applicationContext!!,
                AppDatabase::class.java, "database",
            ).build()
            return database.beerDao()
        }

    val beersRepository: BeersRepository by lazy {
        assert(applicationContext != null)
        val remoteDataSource = BeersRemoteDataSource(apiService)
        val localDataSource = BeersLocalDataSource(remoteDataSource.beersFlow, dao)
        applicationContext = null
        BeersRepository(remoteDataSource, localDataSource)
    }

    fun init(applicationContext: Context) {
        this.applicationContext = applicationContext
    }
}