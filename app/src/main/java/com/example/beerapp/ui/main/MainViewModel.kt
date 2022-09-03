package com.example.beerapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beerapp.data.beer.BeersRepositoryProvider
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val beersRepository = BeersRepositoryProvider.get()

    val beerCollectionFlow = beersRepository.beerCollectionFlow

    init {
        viewModelScope.launch {
            beersRepository.fetch()
        }
    }
}