package com.example.beerapp.data.beer

import com.example.beerapp.data.beer.network.BeersApiService
import com.example.beerapp.data.beer.network.RetrofitProvider

object BeersRepositoryProvider {

    private var instance: BeersRepository? = null

    fun get(): BeersRepository {
        var localInstance = instance
        if (localInstance == null) {
            val beersApiService = RetrofitProvider.instance.create(BeersApiService::class.java)
            val remoteDataSource = BeersRemoteDataSource(beersApiService)
            val localDataSource = BeersLocalDataSource(remoteDataSource.beerCollectionFlow)
            localInstance = BeersRepository(remoteDataSource, localDataSource)
            instance = localInstance
        }
        return localInstance
    }
}