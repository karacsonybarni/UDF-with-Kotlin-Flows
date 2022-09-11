package com.example.beerapp.ui.pager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beerapp.data.beer.BeersRepository
import com.example.beerapp.data.beer.BeersRepositoryProvider
import com.example.beerapp.ui.model.Beer
import com.example.beerapp.ui.util.ModelTransformationUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PagerViewModel(
    private val beersRepository: BeersRepository = BeersRepositoryProvider.get(),
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    lateinit var beerArray: Array<Beer>
        private set
    lateinit var beerMap: Map<Long, Beer>
        private set

    val beersFlow = beersRepository.beerCollectionFlow
        .map { beerCollection ->
            beerCollection.beers.mapValues { entry ->
                ModelTransformationUtil.toBeer(entry.value)
            }
        }
        .onEach { map ->
            beerMap = map

            beerArray = map
                .entries
                .map { it.value }
                .toTypedArray()
        }
        .flowOn(coroutineDispatcher)

    private val _currentItemIndexFlow = MutableStateFlow<Int?>(null)
    val currentItemIndexFlow = _currentItemIndexFlow.asStateFlow()

    private val nextItemIndex: Int get() = (currentItemIndexFlow.value ?: 0) + 1

    val hasNextBeer: Boolean get() = nextItemIndex < beerMap.size

    init {
        viewModelScope.launch {
            beersRepository.fetch()
        }
    }

    fun like(beer: Beer) {
        beersRepository.like(beer.id)
    }

    fun pageToNextBeer() {
        _currentItemIndexFlow.value = nextItemIndex
    }
}