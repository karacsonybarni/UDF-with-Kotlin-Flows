package com.example.beerapp.data

import com.example.beerapp.data.model.BeerDataModelCollection

class BeersRepository(
    private val remoteDataSource: BeersRemoteDataSource,
    private val localDataSource: BeersLocalDataSource
) {

    companion object {
        private const val collectionSize = 10
    }

    val beerCollectionFlow = localDataSource.beerCollectionFlow

    suspend fun fetch() {
        localDataSource.reset()
        remoteDataSource.fetch(collectionSize)
    }

    fun like(id: Long) {
        localDataSource.like(id)
    }

    suspend fun getLikedBeerCollection(): BeerDataModelCollection {
        return localDataSource.getLikedBeerCollection()
    }
}