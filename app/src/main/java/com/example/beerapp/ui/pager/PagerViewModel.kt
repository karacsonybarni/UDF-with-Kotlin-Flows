package com.example.beerapp.ui.pager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beerapp.data.BeersRepository
import com.example.beerapp.data.BeersRepositoryProvider
import com.example.beerapp.ui.model.Beer
import com.example.beerapp.ui.util.ModelConversionUtil.toBeer
import com.example.beerapp.ui.util.ModelConversionUtil.toBeerDataModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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

    val allBeersFlow: StateFlow<Map<Long, Beer>> = beersRepository.allBeersFlow
        .map { beerDataModelMap ->
            beerDataModelMap.mapValues { it.value.toBeer() }
        }
        .onEach {
            if (it.isEmpty()) {
                _currentItemIndexFlow.value = null
            }
        }
        .flowOn(coroutineDispatcher)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    private val allBeers: Map<Long, Beer>
        get() = allBeersFlow.value

    val beerFlow: Flow<Beer> = beersRepository.beerFlow
        .map {
            it.toBeer()
        }
        .flowOn(coroutineDispatcher)

    // Null means that there are no beers so the index is invalid
    private val _currentItemIndexFlow = MutableStateFlow<Int?>(null)
    val currentItemIndexFlow = _currentItemIndexFlow.asStateFlow()

    private val nextItemIndex: Int get() = (currentItemIndexFlow.value ?: 0) + 1

    val hasNextBeer: Boolean get() = nextItemIndex < allBeers.size

    val beers: Collection<Beer>
        get() = allBeers.values

    fun like(beer: Beer) =
        viewModelScope.launch {
            beersRepository.like(beer.toBeerDataModel())
        }

    fun pageToNextBeer() {
        _currentItemIndexFlow.value = nextItemIndex
    }

    fun getBeer(id: Long): Beer? = allBeers[id]

    fun hasBeer(id: Long): Boolean = allBeers.contains(id)
}