package com.example.beerapp.data.source.local.db.currentitemindex

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(currentItem: CurrentItemDbEntity)

    @Query("SELECT * FROM current_item")
    fun selectIndex(): Flow<CurrentItemDbEntity?>
}