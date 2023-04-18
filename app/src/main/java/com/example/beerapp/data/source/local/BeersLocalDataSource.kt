package com.example.beerapp.data.source.local

import com.example.beerapp.data.model.BeerDataModel
import com.example.beerapp.data.source.local.db.BeerDao
import com.example.beerapp.data.source.local.db.BeerDbEntity
import kotlinx.coroutines.flow.first

class BeersLocalDataSource(private val beerDao: BeerDao) {

    private val _likedIds = LinkedHashSet<Long>()
    val likedIds: Collection<Long> = _likedIds

    fun like(id: Long) {
        _likedIds.add(id)
    }

    fun reset() {
        _likedIds.clear()
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