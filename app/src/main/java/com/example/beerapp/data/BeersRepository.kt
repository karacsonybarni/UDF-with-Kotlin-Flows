package com.example.beerapp.data

import com.example.beerapp.data.model.BeerDataModel
import com.example.beerapp.data.source.local.BeersLocalDataSource
import com.example.beerapp.data.source.remote.BeersRemoteDataSource
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class BeersRepository(
    private val remoteDataSource: BeersRemoteDataSource,
    private val localDataSource: BeersLocalDataSource
) {

    companion object {
        private const val collectionSize = 10
    }

    val beersFlow = remoteDataSource.beersFlow
        .onEach {
            it?.let { localDataSource.store(it) }
        }
        .map {
            it ?: localDataSource.getAllBeers()
        }

    suspend fun fetch() {
        localDataSource.reset()
        remoteDataSource.fetch(collectionSize)
    }

    suspend fun like(beer: BeerDataModel) = localDataSource.like(beer)

    suspend fun getLikedBeers() = localDataSource.getLikedBeers()
}