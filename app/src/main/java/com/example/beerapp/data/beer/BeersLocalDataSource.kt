package com.example.beerapp.data.beer

import com.example.beerapp.data.beer.model.BeerDataModel
import com.example.beerapp.data.beer.model.BeerDataModelCollection
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import java.util.*

class BeersLocalDataSource(
    beerCollectionFlow: Flow<BeerDataModelCollection>,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private lateinit var beerCollection: BeerDataModelCollection
    val beerCollectionFlow = beerCollectionFlow.onEach { beerCollection = it }
    private val likedIds = TreeSet<Long>()

    fun like(id: Long) {
        likedIds.add(id)
    }

    suspend fun getLikedBeerCollection(): BeerDataModelCollection =
        withContext(coroutineDispatcher) {
            val beers = beerCollection.beers
            val likedBeers = HashMap<Long, BeerDataModel>()
            for (id in likedIds) {
                beers[id]?.let { likedBeers.put(id, it) }
            }
            BeerDataModelCollection(likedBeers)
        }
}