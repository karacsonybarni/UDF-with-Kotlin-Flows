package com.example.beerapp.data.source.remote

import android.util.Log
import com.example.beerapp.data.model.BeerDataModel
import com.example.beerapp.data.source.remote.network.BeerRemoteEntity
import com.example.beerapp.data.source.remote.network.BeersApiService
import com.example.beerapp.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BeersRemoteDataSource @Inject constructor(
    private val beersApiService: BeersApiService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) {

    private val _beerFlow = MutableSharedFlow<BeerDataModel>()
    val beerFlow = _beerFlow.asSharedFlow()

    suspend fun fetch(collectionSize: Int) =
        withContext(coroutineDispatcher) {
            var i = 0
            while (i < collectionSize) {
                val isFetchSuccessful = fetchABeer()
                if (isFetchSuccessful) {
                    i++
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