package com.example.beerapp.data.beer

import com.example.beerapp.data.beer.model.Beer
import com.example.beerapp.data.beer.model.BeerCollection
import com.example.beerapp.data.beer.network.BeersApiService
import com.example.beerapp.data.beer.network.entity.BeerEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class BeersRemoteDataSource(
    private val beersApiService: BeersApiService,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BeersDataSource {

    companion object {
        private const val collectionSize = 10
    }

    private val _beerCollectionFlow = MutableStateFlow(BeerCollection(emptyArray()))
    override val beerCollectionFlow = _beerCollectionFlow.asStateFlow()

    suspend fun fetch() {
        withContext(coroutineDispatcher) {
            val beers = Array(collectionSize) {
                val beerEntity = beersApiService.getRandomBeer()[0]
                toBeer(beerEntity)
            }
            _beerCollectionFlow.value = BeerCollection(beers)
        }
    }

    private fun toBeer(beerEntity: BeerEntity) = Beer(beerEntity.name)
}