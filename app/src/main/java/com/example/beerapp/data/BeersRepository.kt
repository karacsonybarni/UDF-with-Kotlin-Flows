package com.example.beerapp.data

import com.example.beerapp.data.model.BeerDataModel
import com.example.beerapp.data.source.local.BeersLocalDataSource
import com.example.beerapp.data.source.remote.BeersRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BeersRepository(
    private val remoteDataSource: BeersRemoteDataSource,
    private val localDataSource: BeersLocalDataSource,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    companion object {
        private const val collectionSize = 10
    }

    val beersFlow = remoteDataSource.beersFlow

    suspend fun fetch() {
        localDataSource.reset()
        remoteDataSource.fetch(collectionSize)
    }

    fun like(id: Long) {
        localDataSource.like(id)
    }

    suspend fun getLikedBeers(): Map<Long, BeerDataModel> =
        withContext(coroutineDispatcher) {
            val likedBeers = LinkedHashMap<Long, BeerDataModel>()
            for (id in localDataSource.likedIds) {
                beersFlow.value[id]?.let { likedBeers.put(id, it) }
            }
            likedBeers
        }
}