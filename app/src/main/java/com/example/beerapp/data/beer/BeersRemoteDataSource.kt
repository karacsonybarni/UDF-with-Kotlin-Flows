package com.example.beerapp.data.beer

import com.example.beerapp.data.beer.model.BeerCollection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BeersRemoteDataSource : BeersDataSource {
    private val _beerCollectionFlow = MutableStateFlow(BeerCollection(emptyArray()))
    override val beerCollectionFlow = _beerCollectionFlow.asStateFlow()
}