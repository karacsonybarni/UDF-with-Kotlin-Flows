package com.example.beerapp.data.beer

object BeersRepositoryProvider {

    private var instance: BeersRepository? = null

    fun get(): BeersRepository {
        var localInstance = instance
        if (localInstance == null) {
            localInstance = BeersRepository(BeersRemoteDataSource())
            instance = localInstance
        }
        return localInstance
    }
}