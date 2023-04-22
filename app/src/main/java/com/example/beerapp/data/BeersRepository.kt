package com.example.beerapp.data

import com.example.beerapp.data.model.BeerDataModel
import com.example.beerapp.data.source.local.BeersLocalDataSource
import com.example.beerapp.data.source.remote.BeersRemoteDataSource
import kotlinx.coroutines.flow.onEach

class BeersRepository(
    private val remoteDataSource: BeersRemoteDataSource,
    private val localDataSource: BeersLocalDataSource
) {

    companion object {
        const val COLLECTION_SIZE = 10
    }

    val beerFlow = remoteDataSource.beerFlow
        .onEach {
            it?.let { localDataSource.store(it) }
        }

    suspend fun fetch() {
        localDataSource.reset()
        remoteDataSource.fetch(COLLECTION_SIZE)
    }

    suspend fun like(beer: BeerDataModel) = localDataSource.like(beer)

    suspend fun getLikedBeers() = localDataSource.getLikedBeers()
}