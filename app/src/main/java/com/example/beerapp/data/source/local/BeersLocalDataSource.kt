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
            beerDao.update(toBeerDbEntity(beerDataModel, true))
        }

    suspend fun getLikedBeers(): List<BeerDataModel> =
        withContext(coroutineDispatcher) {
            beerDao.selectLiked().map {
                toBeerDataModel(it)
            }
        }

    suspend fun reset() =
        withContext(coroutineDispatcher) {
            beerDao.deleteAll()
        }

    suspend fun store(beerDataModel: BeerDataModel) =
        withContext(coroutineDispatcher) {
            beerDao.insert(toBeerDbEntity(beerDataModel))
        }

    private fun toBeerDbEntity(beerDataModels: BeerDataModel, isLiked: Boolean = false) =
        BeerDbEntity(
            id = beerDataModels.id,
            name = beerDataModels.name,
            tagline = beerDataModels.tagline,
            imageUrl = beerDataModels.imageUrl,
            isLiked = isLiked
        )

    suspend fun getAllBeers(): Map<Long, BeerDataModel> =
        withContext(coroutineDispatcher) {
            val map = HashMap<Long, BeerDataModel>()
            beerDao.getAll().forEach {
                map[it.id] = toBeerDataModel(it)
            }
            map
        }

    private fun toBeerDataModel(beerDbEntity: BeerDbEntity): BeerDataModel {
        return BeerDataModel(
            id = beerDbEntity.id,
            name = beerDbEntity.name,
            tagline = beerDbEntity.tagline,
            imageUrl = beerDbEntity.imageUrl
        )
    }
}