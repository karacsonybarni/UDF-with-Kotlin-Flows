package com.example.beerapp.data.source.local

import com.example.beerapp.data.model.BeerDataModel
import com.example.beerapp.data.source.local.db.beer.BeerDao
import com.example.beerapp.data.source.local.db.beer.BeerDbEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.Date

class BeersLocalDataSource(
    private val beerDao: BeerDao,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    val allBeersFlow: Flow<Map<Long, BeerDataModel>> =
        beerDao.getAll().map { dbEntityMap ->
            dbEntityMap.mapValues { it.value.toBeerDataModel() }
        }

    suspend fun like(beerDataModel: BeerDataModel) =
        withContext(coroutineDispatcher) {
            val likedBeer = beerDataModel.toBeerDbEntity(isLiked = true)
            beerDao.update(likedBeer)
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

    private fun BeerDataModel.toBeerDbEntity(isLiked: Boolean = false) =
        BeerDbEntity(
            id = id,
            name = name,
            tagline = tagline,
            imageUrl = imageUrl,
            isLiked = isLiked,
            time = Date()
        )

    private fun BeerDbEntity.toBeerDataModel() =
        BeerDataModel(
            id = id,
            name = name,
            tagline = tagline,
            imageUrl = imageUrl
        )
}