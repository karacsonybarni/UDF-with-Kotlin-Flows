package com.example.beerapp.data

import com.example.beerapp.data.model.BeerDataModel
import com.example.beerapp.data.source.local.BeersLocalDataSource
import com.example.beerapp.data.source.local.CurrentItemLocalDataSource
import com.example.beerapp.data.source.remote.BeersRemoteDataSource
import kotlinx.coroutines.flow.onEach

class BeersRepository(
    private val beersRemoteDataSource: BeersRemoteDataSource,
    private val beersLocalDataSource: BeersLocalDataSource,
    private val currentItemLocalDataSource: CurrentItemLocalDataSource
) {

    companion object {
        const val COLLECTION_SIZE = 10
    }

    val beerFlow = beersRemoteDataSource.beerFlow
        .onEach {
            beersLocalDataSource.store(it)
        }

    val allBeersFlow = beersLocalDataSource.allBeersFlow
        .onEach {
            if (it.isEmpty()) {
                setCurrentItemIndex(null)
            }
        }

    val currentItemIndexFlow = currentItemLocalDataSource.currentItemIndexFlow

    suspend fun fetch() {
        beersLocalDataSource.reset()
        beersRemoteDataSource.fetch(COLLECTION_SIZE)
    }

    suspend fun like(beer: BeerDataModel) = beersLocalDataSource.like(beer)

    suspend fun getLikedBeers() = beersLocalDataSource.getLikedBeers()

    suspend fun setCurrentItemIndex(index: Int?) =
        currentItemLocalDataSource.setCurrentItemIndex(index)

    suspend fun isCurrentItemIndexInvalid(): Boolean =
        currentItemLocalDataSource.isCurrentItemIndexInvalid()

    suspend fun invalidateCurrentItemIndex() = setCurrentItemIndex(null)
}