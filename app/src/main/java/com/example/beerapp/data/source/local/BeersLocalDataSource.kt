package com.example.beerapp.data.source.local

import com.example.beerapp.data.model.BeerDataModel
import com.example.beerapp.data.source.local.db.beer.BeerDao
import com.example.beerapp.data.source.local.db.beer.BeerDbEntity
import com.example.beerapp.di.IoDispatcher
import com.example.beerapp.di.IoScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class BeersLocalDataSource @Inject constructor(
    private val beerDao: BeerDao,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    @IoScope coroutineScope: CoroutineScope
) {

    private val beerDbEntitiesFlow: StateFlow<Map<Long, BeerDbEntity>?> = beerDao.getAll()
        .stateIn(coroutineScope, SharingStarted.WhileSubscribed(5000), null)

    val allBeersFlow: Flow<Map<Long, BeerDataModel>> = beerDbEntitiesFlow
        .filterNotNull()
        .map { dbEntityMap ->
            dbEntityMap.mapValues { it.value.toBeerDataModel() }
        }
        .flowOn(coroutineDispatcher)

    suspend fun like(id: Long) =
        withContext(coroutineDispatcher) {
            val likedBeer = beerDbEntitiesFlow.value?.get(id)?.copy(isLiked = true)
            likedBeer?.let {
                beerDao.update(it)
            }
        }

    suspend fun getLikedBeers(): List<BeerDataModel> =
        withContext(coroutineDispatcher) {
            beerDao.selectLiked().map {
                it.toBeerDataModel()
            }
        }

    suspend fun reset() =
        withContext(coroutineDispatcher) {
            beerDao.deleteAll()
        }

    suspend fun store(beerDataModel: BeerDataModel) =
        withContext(coroutineDispatcher) {
            beerDao.insert(beerDataModel.toBeerDbEntity())
        }

    private fun BeerDataModel.toBeerDbEntity() =
        BeerDbEntity(
            id = id,
            name = name,
            tagline = tagline,
            imageUrl = imageUrl,
            time = Date(),
            isLiked = false
        )

    private fun BeerDbEntity.toBeerDataModel() =
        BeerDataModel(
            id = id,
            name = name,
            tagline = tagline,
            imageUrl = imageUrl
        )
}