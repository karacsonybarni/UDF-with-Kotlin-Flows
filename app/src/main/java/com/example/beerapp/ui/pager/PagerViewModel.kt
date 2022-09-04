package com.example.beerapp.ui.pager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beerapp.data.beer.BeersRepository
import com.example.beerapp.data.beer.BeersRepositoryProvider
import com.example.beerapp.data.beer.model.BeerDataModel
import com.example.beerapp.data.beer.model.BeerDataModelCollection
import com.example.beerapp.ui.model.Beer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PagerViewModel(
    private val beersRepository: BeersRepository = BeersRepositoryProvider.get(),
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    val beersFlow = beersRepository.beerCollectionFlow
        .map { toBeerArray(it) }
        .onEach { beers = it }
        .flowOn(coroutineDispatcher)

    private val _positionFlow = MutableStateFlow<Int?>(null)
    val positionFlow = _positionFlow.asStateFlow()

    lateinit var beers: Array<Beer>
        private set

    var onPagerEnd: (() -> Unit)? = null

    init {
        viewModelScope.launch {
            beersRepository.fetch()
        }
    }

    private fun toBeerArray(beerCollection: BeerDataModelCollection): Array<Beer> {
        val beers = beerCollection.beers
        return Array(beers.size) { i ->
            toBeer(beers[i])
        }
    }

    private fun toBeer(beer: BeerDataModel): Beer {
        val likeAction = {
            beersRepository.like(beer.id)
            nextBeer()
        }
        val dislikeAction = {
            nextBeer()
        }
        return Beer(beer.id, beer.name, beer.tagLine, beer.imageUrl, likeAction, dislikeAction)
    }

    private fun nextBeer() {
        val currentPosition = _positionFlow.value ?: 0
        val nextPosition = currentPosition + 1
        if (nextPosition < beers.size) {
            _positionFlow.value = nextPosition
        } else {
            onPagerEnd?.invoke()
        }
    }
}