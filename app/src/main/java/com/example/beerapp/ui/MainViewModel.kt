package com.example.beerapp.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val _appStateFlow = MutableStateFlow(AppState.BeersPager)
    val mainStateFlow = _appStateFlow.asStateFlow()

    fun endPager() {
        _appStateFlow.value = AppState.LikedBeersList
    }
}