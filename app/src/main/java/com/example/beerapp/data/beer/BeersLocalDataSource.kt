package com.example.beerapp.data.beer

import com.example.beerapp.data.beer.model.BeerDataModel
import com.example.beerapp.data.beer.model.BeerDataModelCollection
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import java.util.*

@OptIn(DelicateCoroutinesApi::class)
class BeersLocalDataSource(
    beerCollectionFlow: Flow<BeerDataModelCollection>,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private lateinit var beerCollection: BeerDataModelCollection
    val beerCollectionFlow = beerCollectionFlow.onEach { beerCollection = it }
    private val likedIds = TreeSet<Int>()

    fun like(id: Int) {
        likedIds.add(id)
    }

    suspend fun getLikedBeerCollection(): BeerDataModelCollection =
        withContext(coroutineDispatcher) {
            val beers = beerCollection.beers
            val likedBeers = mutableListOf<BeerDataModel>()
            for (id in likedIds) {
                likedBeers.add(beers[id])
            }
            BeerDataModelCollection(likedBeers.toTypedArray())
        }
}