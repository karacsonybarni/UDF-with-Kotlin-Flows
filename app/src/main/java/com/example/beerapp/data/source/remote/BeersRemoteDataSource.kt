package com.example.beerapp.data.source.remote

import com.example.beerapp.data.model.BeerDataModel
import com.example.beerapp.data.source.remote.network.BeersApiService
import com.example.beerapp.data.source.remote.network.BeerEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class BeersRemoteDataSource(
    private val beersApiService: BeersApiService,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private val _beersFlow = MutableStateFlow(emptyMap<Long, BeerDataModel>())
    val beersFlow = _beersFlow.asStateFlow()

    suspend fun fetch(collectionSize: Int) {
        withContext(coroutineDispatcher) {
            if (_beersFlow.value.isNotEmpty()) {
                _beersFlow.value = emptyMap()
            }
            val beers = HashMap<Long, BeerDataModel>()
            for (i in 0 until collectionSize) {
                val beerEntity = beersApiService.getRandomBeer()[0]
                beers[beerEntity.id] = toBeerDataModel(beerEntity)
            }
            _beersFlow.value = beers
        }
    }

    private fun toBeerDataModel(beerEntity: BeerEntity) =
        BeerDataModel(beerEntity.id, beerEntity.name, beerEntity.tagline, beerEntity.imageUrl)
}