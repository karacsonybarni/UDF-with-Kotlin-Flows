package com.example.beerapp.data

import com.example.beerapp.data.model.BeerDataModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

class BeersLocalDataSource(
    beersFlow: Flow<Map<Long, BeerDataModel>>,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private lateinit var beers: Map<Long, BeerDataModel>
    private var likedIds = LinkedHashSet<Long>()

    val beersFlow = beersFlow
        .onEach {
            // This will be replaced with long term storage
            beers = it
        }

    fun like(id: Long) {
        likedIds.add(id)
    }

    suspend fun getLikedBeers(): Map<Long, BeerDataModel> =
        withContext(coroutineDispatcher) {
            val likedBeers = LinkedHashMap<Long, BeerDataModel>()
            for (id in likedIds) {
                beers[id]?.let { likedBeers.put(id, it) }
            }
            likedBeers
        }

    fun reset() {
        likedIds = LinkedHashSet()
    }
}