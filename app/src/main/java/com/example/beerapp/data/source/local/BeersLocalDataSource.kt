package com.example.beerapp.data.source.local

import com.example.beerapp.data.model.BeerDataModel
import com.example.beerapp.data.source.local.db.BeerDao
import com.example.beerapp.data.source.local.db.BeerDbEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class BeersLocalDataSource(
    private val beerDao: BeerDao,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private val _likedIds = LinkedHashSet<Long>()
    val likedIds: Collection<Long> = _likedIds

    fun like(id: Long) {
        _likedIds.add(id)
    }

    suspend fun reset() {
        _likedIds.clear()
        deleteAll()
    }

    private suspend fun deleteAll() =
        withContext(coroutineDispatcher) {
            beerDao.deleteAll()
        }

    fun store(beerDataModels: Map<Long, BeerDataModel>) {
        beerDao.insertAll(toBeerDbEntities(beerDataModels.values))
    }

    private fun toBeerDbEntities(
        beerDataModels: Collection<BeerDataModel>
    ): Collection<BeerDbEntity> {
        return beerDataModels.map {
            BeerDbEntity(
                id = it.id,
                name = it.name,
                tagline = it.tagline,
                imageUrl = it.imageUrl
            )
        }
    }

    suspend fun getAllBeers(): Map<Long, BeerDataModel> {
        val map = HashMap<Long, BeerDataModel>()
        beerDao.getAll().first().forEach {
            map[it.id] = toBeerDataModel(it)
        }
        return map
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