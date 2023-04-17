package com.example.beerapp.data

import com.example.beerapp.data.model.BeerDataModel
import com.example.beerapp.data.source.local.BeersLocalDataSource
import com.example.beerapp.data.source.remote.BeersRemoteDataSource

class BeersRepository(
    private val remoteDataSource: BeersRemoteDataSource,
    private val localDataSource: BeersLocalDataSource
) {

    companion object {
        private const val collectionSize = 10
    }

    val beersFlow = localDataSource.beersFlow

    suspend fun fetch() {
        localDataSource.reset()
        remoteDataSource.fetch(collectionSize)
    }

    fun like(id: Long) {
        localDataSource.like(id)
    }

    suspend fun getLikedBeers(): Map<Long, BeerDataModel> {
        return localDataSource.getLikedBeers()
    }
}