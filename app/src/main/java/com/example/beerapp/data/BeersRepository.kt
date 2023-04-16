package com.example.beerapp.data

import com.example.beerapp.data.model.BeerDataModel

class BeersRepository(
    private val remoteDataSource: BeersRemoteDataSource,
    private val localDataSource: BeersLocalDataSource
) {

    companion object {
        private const val collectionSize = 1
    }

    val beerCollectionFlow = localDataSource.beersFlow

    suspend fun fetch() {
        localDataSource.reset()
        remoteDataSource.fetch(collectionSize)
    }

    fun like(id: Long) {
        localDataSource.like(id)
    }

    suspend fun getLikedBeers(): Map<Long, BeerDataModel> {
        return localDataSource.getLikedBeerCollection()
    }
}