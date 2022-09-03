package com.example.beerapp.ui.pager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.beerapp.data.beer.BeersRepository
import com.example.beerapp.data.beer.BeersRepositoryProvider
import com.example.beerapp.data.beer.model.BeerDataModel
import com.example.beerapp.data.beer.model.BeerDataModelCollection
import com.example.beerapp.ui.model.Beer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PagerViewModel(
    private val onPagerEnd: () -> Unit,
    private val beersRepository: BeersRepository
) : ViewModel() {

    private class PagerViewModelFactory(
        private val onPagerEnd: () -> Unit,
        private val beersRepository: BeersRepository = BeersRepositoryProvider.get()
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            return PagerViewModel(onPagerEnd, beersRepository) as T
        }
    }

    companion object {

        private var factory: ViewModelProvider.Factory? = null

        fun getFactory(onPagerEnd: () -> Unit): ViewModelProvider.Factory {
            var instance = factory
            if (instance == null) {
                instance = PagerViewModelFactory(onPagerEnd)
                factory = instance
            }
            return instance
        }
    }

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
        val likeAction = {
            beersRepository.like(beer.id)
            nextBeer()
        }
        val dislikeAction = {
            nextBeer()
        }
        return Beer(beer.id, beer.name, likeAction, dislikeAction)
    }

    private fun nextBeer() {
        val currentPosition = _positionFlow.value ?: 0
        val nextPosition = currentPosition + 1
        if (nextPosition < beers.size) {
            _positionFlow.value = nextPosition
        } else {
            onPagerEnd()
        }
    }
}