package com.example.beerapp.ui.list

import androidx.lifecycle.ViewModel
import com.example.beerapp.data.BeersRepository
import com.example.beerapp.di.DefaultDispatcher
import com.example.beerapp.ui.util.ModelConversionUtil.toBeer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BeerListViewModel @Inject constructor(
    private val beersRepository: BeersRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    suspend fun getLikedBeers() =
        withContext(dispatcher) {
            beersRepository.getLikedBeers().map {
                it.toBeer()
            }
        }
}