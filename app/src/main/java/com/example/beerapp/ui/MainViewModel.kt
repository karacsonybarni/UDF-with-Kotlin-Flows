package com.example.beerapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.beerapp.data.BeersRepository
import com.example.beerapp.data.BeersRepositoryProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val beersRepository: BeersRepository
) : ViewModel() {

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                BeersRepositoryProvider.applicationContext = application
                return MainViewModel(BeersRepositoryProvider.beersRepository) as T
            }
        }
    }

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