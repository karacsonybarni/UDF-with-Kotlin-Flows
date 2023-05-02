package com.example.beerapp.data

import com.example.beerapp.data.source.local.BeersLocalDataSource
import com.example.beerapp.data.source.local.CurrentItemLocalDataSource
import com.example.beerapp.data.source.remote.BeersRemoteDataSource
import com.example.beerapp.di.IoDispatcher
import com.example.beerapp.di.IoScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BeersRepository @Inject constructor(
    private val beersRemoteDataSource: BeersRemoteDataSource,
    private val beersLocalDataSource: BeersLocalDataSource,
    private val currentItemLocalDataSource: CurrentItemLocalDataSource,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    @IoScope private val coroutineScope: CoroutineScope
) {

    companion object {
        const val COLLECTION_SIZE = 10
    }

    val allBeersFlow = beersLocalDataSource.allBeersFlow
        .onEach {
            if (it.isEmpty()) {
                setCurrentItemIndex(null)
            }
        }

    val currentItemIndexFlow = currentItemLocalDataSource.currentItemIndexFlow

    init {
        coroutineScope.launch(coroutineDispatcher) {
            beersRemoteDataSource.beerFlow.collect {
                beersLocalDataSource.store(it)
            }
        }
    }

    suspend fun fetch() {
        beersLocalDataSource.reset()
        beersRemoteDataSource.fetch(COLLECTION_SIZE)
    }

    suspend fun like(id: Long) = beersLocalDataSource.like(id)

    suspend fun getLikedBeers() = beersLocalDataSource.getLikedBeers()

    suspend fun setCurrentItemIndex(index: Int?) =
        currentItemLocalDataSource.setCurrentItemIndex(index)
}