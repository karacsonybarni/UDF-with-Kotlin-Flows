package com.example.beerapp.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val _appStateFlow = MutableStateFlow(AppState.BeersPager)
    val appStateFlow = _appStateFlow.asStateFlow()

    fun navigateToLikedBeerList() {
        _appStateFlow.value = AppState.LikedBeersList
    }

    fun navigateToBeerPager() {
        _appStateFlow.value = AppState.BeersPager
    }
}