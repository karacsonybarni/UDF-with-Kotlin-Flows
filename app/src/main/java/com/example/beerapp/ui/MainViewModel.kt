package com.example.beerapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beerapp.data.BeersRepository
import com.example.beerapp.data.BeersRepositoryProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val beersRepository: BeersRepository = BeersRepositoryProvider.beersRepository
) : ViewModel() {

    private val _appStateFlow = MutableStateFlow(AppState.BeersPager)
    val appStateFlow = _appStateFlow.asStateFlow()

    init {
        fetchBeers()
    }

    fun navigateToLikedBeerList() {
        _appStateFlow.value = AppState.LikedBeersList
    }

    fun navigateToBeerPager() {
        _appStateFlow.value = AppState.BeersPager
        fetchBeers()
    }

    private fun fetchBeers() = viewModelScope.launch { beersRepository.fetch() }
}