package com.example.beerapp.data

import com.example.beerapp.data.source.remote.network.BeersApiService
import com.example.beerapp.data.source.remote.network.RetrofitProvider
import com.example.beerapp.data.source.local.BeersLocalDataSource
import com.example.beerapp.data.source.remote.BeersRemoteDataSource

object BeersRepositoryProvider {

    val beersRepository: BeersRepository by lazy {
        val beersApiService = RetrofitProvider.instance.create(BeersApiService::class.java)
        val remoteDataSource = BeersRemoteDataSource(beersApiService)
        val localDataSource = BeersLocalDataSource(remoteDataSource.beersFlow)
        BeersRepository(remoteDataSource, localDataSource)
    }
}