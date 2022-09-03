package com.example.beerapp.data.beer

import com.example.beerapp.data.beer.model.BeerDataModel
import com.example.beerapp.data.beer.model.BeerDataModelCollection
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@OptIn(DelicateCoroutinesApi::class)
class BeersLocalDataSource(
    val beerCollectionFlow: Flow<BeerDataModelCollection>,
    coroutineScope: CoroutineScope = GlobalScope,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private lateinit var beerCollection: BeerDataModelCollection
    private val likedIds = TreeSet<Int>()

    init {
        coroutineScope.launch(coroutineDispatcher) {
            beerCollectionFlow.collect {
                beerCollection = it
            }
        }
    }

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