package com.example.beerapp.data.source.local

import com.example.beerapp.data.source.local.db.BeerDao

class BeersLocalDataSource(
    beerDao: BeerDao
) {

    private val _likedIds = LinkedHashSet<Long>()
    val likedIds: Collection<Long> = _likedIds

    fun like(id: Long) {
        _likedIds.add(id)
    }

    fun reset() {
        _likedIds.clear()
    }
}