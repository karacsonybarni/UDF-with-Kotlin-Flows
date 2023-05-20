package com.example.beerapp.ui.swiper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beerapp.data.BeersRepository
import com.example.beerapp.di.DefaultDispatcher
import com.example.beerapp.ui.model.Beer
import com.example.beerapp.ui.util.ModelConversionUtil.toBeer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SwiperViewModel @Inject constructor(
    private val beersRepository: BeersRepository,
    @DefaultDispatcher coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    val uiStateFlow: StateFlow<SwiperUiState> = beersRepository.allBeersFlow
        .map { beerDataModelMap ->
            beerDataModelMap.mapValues { it.value.toBeer() }
        }
        .combine(beersRepository.currentItemIndexFlow) { beers, currentItemIndex ->
            SwiperUiState(beers, currentItemIndex)
        }
        .flowOn(coroutineDispatcher)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            SwiperUiState(emptyMap(), null)
        )

    val uiState: SwiperUiState get() = uiStateFlow.value

    fun like(beer: Beer) =
        viewModelScope.launch {
            beersRepository.like(beer.id)
        }

    fun pageToNextBeer() =
        viewModelScope.launch {
            beersRepository.setCurrentItemIndex(uiState.nextItemIndex)
        }
}