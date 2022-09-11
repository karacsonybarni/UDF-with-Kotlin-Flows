package com.example.beerapp.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val _mainStateFlow = MutableStateFlow(MainState.BeersPager)
    val mainStateFlow = _mainStateFlow.asStateFlow()

    fun endPager() {
        _mainStateFlow.value = MainState.LikedBeersList
    }
}