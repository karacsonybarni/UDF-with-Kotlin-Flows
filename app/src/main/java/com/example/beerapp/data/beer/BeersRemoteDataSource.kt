package com.example.beerapp.data.beer

import com.example.beerapp.data.beer.model.BeerDataModel
import com.example.beerapp.data.beer.model.BeerDataModelCollection
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
) {

    companion object {
        private const val collectionSize = 10
    }

    private val _beerCollectionFlow = MutableStateFlow(BeerDataModelCollection(emptyArray()))
    val beerCollectionFlow = _beerCollectionFlow.asStateFlow()

    suspend fun fetch() {
        withContext(coroutineDispatcher) {
            val beers = Array(collectionSize) { i ->
                val beerEntity = beersApiService.getRandomBeer()[0]
                toBeer(i, beerEntity)
            }
            _beerCollectionFlow.value = BeerDataModelCollection(beers)
        }
    }

    private fun toBeer(id: Int, beerEntity: BeerEntity) = BeerDataModel(id, beerEntity.name)
}