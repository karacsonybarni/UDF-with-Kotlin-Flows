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

    private val _currentItemIndexFlow = MutableStateFlow<Int?>(null)
    val currentItemIndexFlow = _currentItemIndexFlow.asStateFlow()

    private val nextItemIndex: Int get() = (currentItemIndexFlow.value ?: 0) + 1

    val hasNextBeer: Boolean get() = nextItemIndex < beers.size

    lateinit var beers: Array<Beer>
        private set

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
        return Beer(beer.id, beer.name, beer.tagLine, beer.imageUrl)
    }

    fun like(beer: Beer) {
        beersRepository.like(beer.id)
    }

    fun pageToNextBeer() {
        _currentItemIndexFlow.value = nextItemIndex
    }
}