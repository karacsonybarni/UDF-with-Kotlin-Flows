package com.example.beerapp.ui.pager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beerapp.data.BeersRepository
import com.example.beerapp.data.BeersRepositoryProvider
import com.example.beerapp.ui.model.Beer
import com.example.beerapp.ui.util.ModelConversionUtil.toBeer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PagerViewModel(
    private val beersRepository: BeersRepository = BeersRepositoryProvider.beersRepository,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    val uiStateFlow: StateFlow<PagerUiState> = beersRepository.allBeersFlow
        .map { beerDataModelMap ->
            beerDataModelMap.mapValues { it.value.toBeer() }
        }
        .combine(beersRepository.currentItemIndexFlow) { beers, currentItemIndex ->
            PagerUiState(beers, currentItemIndex)
        }
        .flowOn(coroutineDispatcher)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            PagerUiState(emptyMap(), null)
        )

    val uiState: PagerUiState get() = uiStateFlow.value

    val beerFlow: Flow<Beer> = beersRepository.beerFlow
        .map {
            it.toBeer()
        }
        .flowOn(coroutineDispatcher)

    fun like(beer: Beer) =
        viewModelScope.launch {
            beersRepository.like(beer.id)
        }

    fun pageToNextBeer() =
        viewModelScope.launch {
            beersRepository.setCurrentItemIndex(uiState.nextItemIndex)
        }
}