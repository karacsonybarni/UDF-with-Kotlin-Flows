package com.example.beerapp.data.source.local

import com.example.beerapp.data.model.BeerDataModel
import com.example.beerapp.data.source.local.db.BeerDao
import com.example.beerapp.data.source.local.db.BeerDbEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BeersLocalDataSource(
    private val beerDao: BeerDao,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

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
            isLiked = isLiked
        )

    suspend fun getAllBeers(): Map<Long, BeerDataModel> =
        withContext(coroutineDispatcher) {
            val map = HashMap<Long, BeerDataModel>()
            beerDao.getAll().forEach {
                map[it.id] = it.toBeerDataModel()
            }
            map
        }

    private fun BeerDbEntity.toBeerDataModel() =
        BeerDataModel(
            id = id,
            name = name,
            tagline = tagline,
            imageUrl = imageUrl
        )
}