package com.example.beerapp.ui.pager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beerapp.data.BeersRepository
import com.example.beerapp.data.BeersRepositoryProvider
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

    private lateinit var beers: Map<Long, Beer>

    // Needs to be a StateFlow<Map<Long, Beer>> because the PagerFragment uses the Long ids
    // of the Beers
    val beersFlow: StateFlow<Map<Long, Beer>> = beersRepository.beerCollectionFlow
        .map { beerCollection ->
            beerCollection.mapValues { entry ->
                ModelTransformationUtil.toBeer(entry.value)
            }
        }
        .onEach { map ->
            beers = map
        }
        .flowOn(coroutineDispatcher)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyMap())

    private val _currentItemIndexFlow = MutableStateFlow<Int?>(null)
    val currentItemIndexFlow = _currentItemIndexFlow.asStateFlow()

    private val nextItemIndex: Int get() = (currentItemIndexFlow.value ?: 0) + 1

    val hasNextBeer: Boolean get() = nextItemIndex < beers.size

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

    fun getBeer(id: Long): Beer = beers[id]!!
}