package com.example.beerapp.ui.list

import androidx.lifecycle.ViewModel
import com.example.beerapp.data.beer.BeersRepository
import com.example.beerapp.data.beer.BeersRepositoryProvider
import com.example.beerapp.data.beer.model.BeerDataModel
import com.example.beerapp.data.beer.model.BeerDataModelCollection
import com.example.beerapp.ui.model.Beer

class ListViewModel(private val beersRepository: BeersRepository = BeersRepositoryProvider.get()) :
    ViewModel() {

    suspend fun getLikedBeers(): Array<Beer> {
        val beerCollection = beersRepository.getLikedBeerCollection()
        return toBeerArray(beerCollection)
    }

    private fun toBeerArray(beerCollection: BeerDataModelCollection): Array<Beer> {
        val beers = beerCollection.beers
        return Array(beers.size) { i ->
            toBeer(beers[i])
        }
    }

    private fun toBeer(beer: BeerDataModel): Beer {
        return Beer(beer.id, beer.name, {}, {})
    }
}