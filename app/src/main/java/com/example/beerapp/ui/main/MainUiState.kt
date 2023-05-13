package com.example.beerapp.ui.main

data class MainUiState(val activeFragment: ActiveFragment, val isDisplayHomeAsUpEnabled: Boolean) {
    enum class ActiveFragment {
        BeersPager,
        LikedBeersList
    }
}