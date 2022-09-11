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

    private val _beerCollectionFlow = MutableStateFlow(BeerDataModelCollection(emptyMap()))
    val beerCollectionFlow = _beerCollectionFlow.asStateFlow()

    suspend fun fetch(collectionSize: Int) {
        withContext(coroutineDispatcher) {
            val beers = HashMap<Long, BeerDataModel>()
            for (i in 0 until collectionSize) {
                val beerEntity = beersApiService.getRandomBeer()[0]
                beers[beerEntity.id] = toBeer(beerEntity)
            }
            _beerCollectionFlow.value = BeerDataModelCollection(beers)
        }
    }

    private fun toBeer(beerEntity: BeerEntity) =
        BeerDataModel(beerEntity.id, beerEntity.name, beerEntity.tagline, beerEntity.imageUrl)
}