package com.example.beerapp.data.beer

import com.example.beerapp.data.beer.network.BeersApiService
import com.example.beerapp.data.beer.network.RetrofitProvider

object BeersRepositoryProvider {

    private var instance: BeersRepository? = null

    fun get(): BeersRepository {
        var localInstance = instance
        if (localInstance == null) {
            val beersApiService = RetrofitProvider.instance.create(BeersApiService::class.java)
            localInstance = BeersRepository(BeersRemoteDataSource(beersApiService))
            instance = localInstance
        }
        return localInstance
    }
}