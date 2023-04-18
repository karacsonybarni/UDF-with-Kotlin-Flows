package com.example.beerapp.ui.pager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beerapp.data.BeersRepository
import com.example.beerapp.data.BeersRepositoryProvider
import com.example.beerapp.data.model.BeerDataModel
import com.example.beerapp.ui.model.Beer
import com.example.beerapp.ui.util.ModelTransformationUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PagerViewModel(
    private val beersRepository: BeersRepository = BeersRepositoryProvider.beersRepository,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    // Needs to be a StateFlow<Map<Long, Beer>> because the PagerFragment uses the Long ids
    // of the Beers
    val beersFlow: StateFlow<Map<Long, Beer>> = beersRepository.beersFlow
        .map { beers: Map<Long, BeerDataModel> ->
            beers.mapValues { entry ->
                ModelTransformationUtil.toBeer(entry.value)
            }
        }
        .onEach { beers ->
            if (beers.isEmpty()) {
                _currentItemIndexFlow.value = null
            }
        }
        .flowOn(coroutineDispatcher)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    private val _currentItemIndexFlow = MutableStateFlow<Int?>(null)
    val currentItemIndexFlow = _currentItemIndexFlow.asStateFlow()

    private val nextItemIndex: Int get() = (currentItemIndexFlow.value ?: 0) + 1

    val hasNextBeer: Boolean get() = nextItemIndex < beersFlow.value.size

    fun like(beer: Beer) =
        viewModelScope.launch {
            beersRepository.like(toBeerDataModel(beer))
        }

    private fun toBeerDataModel(beer: Beer) =
        BeerDataModel(
            beer.id,
            beer.name,
            beer.tagline,
            beer.imageUrl
        )

    fun pageToNextBeer() {
        _currentItemIndexFlow.value = nextItemIndex
    }

    fun getBeer(id: Long): Beer? = beersFlow.value[id]
}