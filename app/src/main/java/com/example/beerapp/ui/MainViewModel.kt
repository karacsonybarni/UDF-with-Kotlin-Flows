package com.example.beerapp.ui

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

    private val _appStateFlow = MutableStateFlow(AppState.BeersPager)
    val appStateFlow = _appStateFlow.asStateFlow()

    var isDisplayHomeAsUpEnabled = false

    init {
        _appStateFlow.value = AppState.BeersPager
    }

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
        isDisplayHomeAsUpEnabled = true
        _appStateFlow.value = AppState.LikedBeersList
    }

    private fun invalidateCurrentItemIndex() =
        viewModelScope.launch {
            beersRepository.setCurrentItemIndex(null)
        }

    fun onNavigateToBeerPager() {
        fetchBeers()
        isDisplayHomeAsUpEnabled = false
        _appStateFlow.value = AppState.BeersPager
    }
}