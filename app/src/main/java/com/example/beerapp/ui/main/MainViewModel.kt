package com.example.beerapp.ui.main

import androidx.lifecycle.ViewModel
import com.example.beerapp.data.beer.BeersRepositoryProvider

class MainViewModel : ViewModel() {
    val beerCollectionFlow = BeersRepositoryProvider.get().beerCollectionFlow
}