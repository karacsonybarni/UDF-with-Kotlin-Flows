package com.example.beerapp.ui.main

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beerapp.data.BeersRepository
import com.example.beerapp.di.DefaultDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val beersRepository: BeersRepository,
    @DefaultDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    private var initializeCalled = false

    private val beersPagerUiState =
        MainUiState(MainUiState.ActiveFragment.BeersPager, false)
    private val likedBeersListUiState =
        MainUiState(MainUiState.ActiveFragment.LikedBeersList, true)

    private val _uiStateFlow = MutableStateFlow(beersPagerUiState)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    @MainThread
    fun initialize() {
        if(initializeCalled) return
        initializeCalled = true

        viewModelScope.launch(coroutineDispatcher) {
            if (!hasSavedState()) {
                fetchBeers()
            }
        }
    }

    private suspend fun hasSavedState(): Boolean =
        beersRepository.currentItemIndexFlow.first() != null

    private fun fetchBeers() =
        viewModelScope.launch {
            beersRepository.fetch()
        }

    fun onNavigateToLikedBeerList() {
        invalidateCurrentItemIndex()
        _uiStateFlow.value = likedBeersListUiState
    }

    private fun invalidateCurrentItemIndex() =
        viewModelScope.launch {
            beersRepository.setCurrentItemIndex(null)
        }

    fun onNavigateToBeerPager() {
        fetchBeers()
        _uiStateFlow.value = beersPagerUiState
    }
}