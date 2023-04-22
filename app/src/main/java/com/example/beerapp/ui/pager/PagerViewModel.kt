package com.example.beerapp.ui.pager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beerapp.data.BeersRepository
import com.example.beerapp.data.BeersRepositoryProvider
import com.example.beerapp.data.model.BeerDataModel
import com.example.beerapp.ui.model.Beer
import com.example.beerapp.ui.util.ModelTransformationUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PagerViewModel(
    private val beersRepository: BeersRepository = BeersRepositoryProvider.beersRepository,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    val beerMap = LinkedHashMap<Long, Beer>()

    val beerFlow: Flow<Beer?> = beersRepository.beerFlow
        .map {
            it?.let {
                ModelTransformationUtil.toBeer(it)
            }
        }
        .onEach { beer ->
            if (beer != null) {
                beerMap[beer.id] = beer
            } else {
                _currentItemIndexFlow.value = null
                beerMap.clear()
            }
        }
        .flowOn(coroutineDispatcher)

    // Null means that there are no beers so the index is invalid
    private val _currentItemIndexFlow = MutableStateFlow<Int?>(null)
    val currentItemIndexFlow = _currentItemIndexFlow.asStateFlow()

    private val nextItemIndex: Int get() = (currentItemIndexFlow.value ?: 0) + 1

    val hasNextBeer: Boolean get() = nextItemIndex < beerMap.size

    fun like(beer: Beer) =
        viewModelScope.launch {
            beersRepository.like(toBeerDataModel(beer))
        }

    private fun toBeerDataModel(beer: Beer) =
        BeerDataModel(
            beer.id,
            beer.name,
            beer.tagline,
            beer.imageUrl
        )

    fun pageToNextBeer() {
        _currentItemIndexFlow.value = nextItemIndex
    }

    fun getBeer(id: Long): Beer? = beerMap[id]
}