package com.example.beerapp.data.beer

import com.example.beerapp.data.beer.model.BeerDataModelCollection
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@OptIn(DelicateCoroutinesApi::class)
class BeersLocalDataSource(
    val beerCollectionFlow: Flow<BeerDataModelCollection>,
    coroutineScope: CoroutineScope = GlobalScope,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private var beerCollection: BeerDataModelCollection? = null
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
}