package com.example.beerapp.ui.list

import androidx.lifecycle.ViewModel
import com.example.beerapp.data.BeersRepository
import com.example.beerapp.data.BeersRepositoryProvider
import com.example.beerapp.ui.util.ModelTransformationUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BeerListViewModel(
    private val beersRepository: BeersRepository = BeersRepositoryProvider.get(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    suspend fun getLikedBeers() = withContext(dispatcher) {
        beersRepository.getLikedBeerCollection().beers.mapValues {
            ModelTransformationUtil.toBeer(it.value)
        }
    }
}