package com.example.beerapp.data.beer

class BeersRepository(private val beersDataSource: BeersRemoteDataSource) {

    val beerCollectionFlow = beersDataSource.beerCollectionFlow

    suspend fun fetch() {
        beersDataSource.fetch()
    }
}