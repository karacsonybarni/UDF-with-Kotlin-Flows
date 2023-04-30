package com.example.beerapp.ui.pager

import com.example.beerapp.ui.model.Beer

// Null currentItemIndex means that there are no beers so the index is invalid
data class PagerUiState(val beerMap: Map<Long, Beer>, val currentItemIndex: Int?) {

    val nextItemIndex: Int get() = (currentItemIndex ?: 0) + 1

    val hasNextItem: Boolean get() = nextItemIndex < beerMap.size
}