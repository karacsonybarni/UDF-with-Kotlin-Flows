package com.example.beerapp.data.beer

import com.example.beerapp.data.beer.model.BeerCollection
import kotlinx.coroutines.flow.Flow

interface BeersDataSource {
    val beerCollectionFlow: Flow<BeerCollection>
}