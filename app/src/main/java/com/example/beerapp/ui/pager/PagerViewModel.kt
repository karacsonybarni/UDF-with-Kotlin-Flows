package com.example.beerapp.ui.pager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beerapp.data.beer.BeersRepositoryProvider
import kotlinx.coroutines.launch

class PagerViewModel : ViewModel() {

    private val beersRepository = BeersRepositoryProvider.get()

    val beerCollectionFlow = beersRepository.beerCollectionFlow

    init {
        viewModelScope.launch {
            beersRepository.fetch()
        }
    }
}