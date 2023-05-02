package com.example.beerapp.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.example.beerapp.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrentItemLocalDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) {

    private val CURRENT_ITEM_INDEX = intPreferencesKey("current_item_index")

    // Null means that there are no beers so the index is invalid
    val currentItemIndexFlow: Flow<Int?> = dataStore.data
        .map { preferences ->
            val rawIndex = preferences[CURRENT_ITEM_INDEX] ?: -1
            if (rawIndex >= 0) rawIndex else null
        }

    suspend fun setCurrentItemIndex(index: Int?) =
        withContext(coroutineDispatcher) {
            dataStore.edit { preferences ->
                preferences[CURRENT_ITEM_INDEX] = index ?: -1
            }
        }
}