package com.example.beerapp.data.beer

import com.example.beerapp.data.beer.model.BeerDataModelCollection

class BeersRepository(
    private val remoteDataSource: BeersRemoteDataSource,
    private val localDataSource: BeersLocalDataSource
) {

    companion object {
        private const val collectionSize = 1
    }

    val beerCollectionFlow = localDataSource.beerCollectionFlow

    suspend fun fetch() {
        remoteDataSource.fetch(collectionSize)
    }

    fun like(id: Int) {
        localDataSource.like(id)
    }

    suspend fun getLikedBeerCollection(): BeerDataModelCollection {
        return localDataSource.getLikedBeerCollection()
    }
}