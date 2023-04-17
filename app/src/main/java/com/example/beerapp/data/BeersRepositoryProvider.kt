package com.example.beerapp.data

import com.example.beerapp.data.source.remote.network.BeersApiService
import com.example.beerapp.data.source.remote.network.RetrofitProvider
import com.example.beerapp.data.source.local.BeersLocalDataSource
import com.example.beerapp.data.source.remote.BeersRemoteDataSource

object BeersRepositoryProvider {

    private var instance: BeersRepository? = null

    fun get(): BeersRepository {
        var localInstance = instance
        if (localInstance == null) {
            val beersApiService = RetrofitProvider.instance.create(BeersApiService::class.java)
            val remoteDataSource = BeersRemoteDataSource(beersApiService)
            val localDataSource = BeersLocalDataSource(remoteDataSource.beersFlow)
            localInstance = BeersRepository(remoteDataSource, localDataSource)
            instance = localInstance
        }
        return localInstance
    }
}