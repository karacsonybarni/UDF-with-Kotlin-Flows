package com.example.beerapp.data.source.remote

import android.util.Log
import com.example.beerapp.data.model.BeerDataModel
import com.example.beerapp.data.source.remote.network.BeerRemoteEntity
import com.example.beerapp.data.source.remote.network.BeersApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.withContext

class BeersRemoteDataSource(
    private val beersApiService: BeersApiService,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    // Null means that fetching has started
    private val _beerFlow = MutableSharedFlow<BeerDataModel?>()
    val beerFlow = _beerFlow.asSharedFlow()

    suspend fun fetch(collectionSize: Int) {
        withContext(coroutineDispatcher) {
            _beerFlow.emit(null)

            var i = 0
            while (i < collectionSize) {
                if (fetchABeer()) {
                    i++
                }
            }
        }
    }

    private suspend fun fetchABeer(): Boolean {
        return try {
            val beerEntity = beersApiService.getRandomBeer()[0]
            _beerFlow.emit(beerEntity.toBeerDataModel())
            true
        } catch (e: Exception) {
            Log.d(BeersRemoteDataSource::class.java.name, e.message, e)
            false
        }
    }

    private fun BeerRemoteEntity.toBeerDataModel() =
        BeerDataModel(
            id = id,
            name = name,
            tagline = tagline,
            imageUrl = imageUrl
        )
}