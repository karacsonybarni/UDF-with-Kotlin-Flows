package com.example.beerapp.ui.pager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beerapp.data.beer.BeersRepositoryProvider
import com.example.beerapp.data.beer.model.BeerDataModel
import com.example.beerapp.data.beer.model.BeerDataModelCollection
import com.example.beerapp.ui.model.Beer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PagerViewModel : ViewModel() {

    private val beersRepository = BeersRepositoryProvider.get()

    private val _beersFlow = MutableStateFlow<Array<Beer>>(emptyArray())
    val beersFlow = _beersFlow.asStateFlow()

    private val _positionFlow = MutableStateFlow<Int?>(null)
    val positionFlow = _positionFlow.asStateFlow()

    lateinit var beers: Array<Beer>
        private set

    init {
        viewModelScope.launch {
            beersRepository.fetch()
            beersRepository.beerCollectionFlow
                .map { toBeerArray(it) }
                .collect {
                    beers = it
                    _beersFlow.value = it
                }
        }
    }

    private fun toBeerArray(beerCollection: BeerDataModelCollection): Array<Beer> {
        val beers = beerCollection.beers
        return Array(beers.size) { i ->
            toBeer(beers[i])
        }
    }

    private fun toBeer(beer: BeerDataModel): Beer {
        return Beer(beer.id, beer.name) {
            beersRepository.like(beer.id)
            nextBeer()
        }
    }

    private fun nextBeer() {
        val currentPosition = _positionFlow.value ?: 0
        val nextPosition = currentPosition + 1
        if (nextPosition < beers.size) {
            _positionFlow.value = nextPosition
        }
    }
}