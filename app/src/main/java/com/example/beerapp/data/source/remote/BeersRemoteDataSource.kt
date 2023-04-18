package com.example.beerapp.data.source.remote

import com.example.beerapp.data.model.BeerDataModel
import com.example.beerapp.data.source.remote.network.BeersApiService
import com.example.beerapp.data.source.remote.network.BeerRemoteEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class BeersRemoteDataSource(
    private val beersApiService: BeersApiService,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    // Null means that the fetching failed
    private val _beersFlow = MutableStateFlow<Map<Long, BeerDataModel>?>(emptyMap())
    val beersFlow = _beersFlow.asStateFlow()

    suspend fun fetch(collectionSize: Int) {
        withContext(coroutineDispatcher) {
            val value = _beersFlow.value
            if (value == null || value.isNotEmpty()) {
                _beersFlow.value = emptyMap()
            }

            val beers = HashMap<Long, BeerDataModel>()
            try {
                for (i in 0 until collectionSize) {
                    val beerEntity = beersApiService.getRandomBeer()[0]
                    beers[beerEntity.id] = toBeerDataModel(beerEntity)
                }
                _beersFlow.value = beers
            } catch (e: Exception) {
                _beersFlow.value = null
            }
        }
    }

    private fun toBeerDataModel(beerRemoteEntity: BeerRemoteEntity) =
        BeerDataModel(
            beerRemoteEntity.id,
            beerRemoteEntity.name,
            beerRemoteEntity.tagline,
            beerRemoteEntity.imageUrl
        )
}