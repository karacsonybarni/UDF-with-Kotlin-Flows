package com.example.beerapp.data.beer

class BeersRepository(
    private val remoteDataSource: BeersRemoteDataSource,
    private val localDataSource: BeersLocalDataSource
) {

    val beerCollectionFlow = localDataSource.beerCollectionFlow

    suspend fun fetch() {
        remoteDataSource.fetch()
    }

    fun like(id: Int) {
        localDataSource.like(id)
    }
}