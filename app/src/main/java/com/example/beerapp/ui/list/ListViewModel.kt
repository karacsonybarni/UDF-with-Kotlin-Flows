package com.example.beerapp.ui.list

import androidx.lifecycle.ViewModel
import com.example.beerapp.data.beer.BeersRepository
import com.example.beerapp.data.beer.BeersRepositoryProvider
import com.example.beerapp.data.beer.model.BeerDataModel
import com.example.beerapp.data.beer.model.BeerDataModelCollection
import com.example.beerapp.ui.model.Beer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ListViewModel(
    private val beersRepository: BeersRepository = BeersRepositoryProvider.get(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) :
    ViewModel() {

    suspend fun getLikedBeers() = withContext(dispatcher) {
        val beerCollection = beersRepository.getLikedBeerCollection()
        toBeerArray(beerCollection)
    }

    private fun toBeerArray(beerCollection: BeerDataModelCollection): Array<Beer> {
        val beers = beerCollection.beers
        return Array(beers.size) { i ->
            toBeer(beers[i])
        }
    }

    private fun toBeer(beer: BeerDataModel): Beer {
        return Beer(beer.id, beer.name, beer.tagLine, beer.imageUrl)
    }
}