package com.example.beerapp.data.beer

class BeersRepository(
    private val remoteDataSource: BeersRemoteDataSource,
    private val localDataSource: BeersLocalDataSource
) {

    companion object {
        private const val collectionSize = 10
    }

    val beerCollectionFlow = localDataSource.beerCollectionFlow

    suspend fun fetch() {
        remoteDataSource.fetch(collectionSize)
    }

    fun like(id: Int) {
        localDataSource.like(id)
    }
}