package com.example.beerapp.data.beer

class BeersRepository(beersDataSource: BeersDataSource) {
    val beerCollectionFlow = beersDataSource.beerCollectionFlow
}