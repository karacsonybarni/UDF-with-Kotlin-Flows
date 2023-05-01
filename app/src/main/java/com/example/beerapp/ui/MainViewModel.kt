package com.example.beerapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beerapp.data.BeersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val beersRepository: BeersRepository
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