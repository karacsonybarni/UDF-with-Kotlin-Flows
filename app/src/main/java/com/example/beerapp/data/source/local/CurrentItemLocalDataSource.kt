package com.example.beerapp.data.source.local

import com.example.beerapp.data.source.local.db.currentitemindex.CurrentItemDao
import com.example.beerapp.data.source.local.db.currentitemindex.CurrentItemDbEntity
import com.example.beerapp.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrentItemLocalDataSource @Inject constructor(
    private val currentItemDao: CurrentItemDao,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) {

    // Null means that there are no beers so the index is invalid
    val currentItemIndexFlow: Flow<Int?> = currentItemDao.selectIndex().map {
        it?.index
    }

    suspend fun setCurrentItemIndex(index: Int?) =
        withContext(coroutineDispatcher) {
            val currentItem = CurrentItemDbEntity(index = index)
            currentItemDao.update(currentItem)
        }
}