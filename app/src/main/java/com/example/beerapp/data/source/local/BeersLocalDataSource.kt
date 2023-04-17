package com.example.beerapp.data.source.local

import com.example.beerapp.data.model.BeerDataModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

class BeersLocalDataSource(
    val beersFlow: StateFlow<Map<Long, BeerDataModel>>,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private var likedIds = LinkedHashSet<Long>()

    fun like(id: Long) {
        likedIds.add(id)
    }

    suspend fun getLikedBeers(): Map<Long, BeerDataModel> =
        withContext(coroutineDispatcher) {
            val likedBeers = LinkedHashMap<Long, BeerDataModel>()
            for (id in likedIds) {
                beersFlow.value[id]?.let { likedBeers.put(id, it) }
            }
            likedBeers
        }

    fun reset() {
        likedIds = LinkedHashSet()
    }
}