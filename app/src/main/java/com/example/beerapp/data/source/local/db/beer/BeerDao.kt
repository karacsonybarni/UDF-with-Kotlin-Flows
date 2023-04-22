package com.example.beerapp.data.source.local.db.beer

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.MapInfo
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BeerDao {

    @MapInfo(keyColumn = "id")
    @Query("SELECT * FROM beer ORDER BY time ASC")
    fun getAll(): Flow<Map<Long, BeerDbEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(beerDbEntities: BeerDbEntity)

    @Query("DELETE FROM beer")
    fun deleteAll()

    @Update
    fun update(beer: BeerDbEntity)

    @Query("SELECT * FROM beer WHERE isLiked = 1 ORDER BY time ASC")
    fun selectLiked(): List<BeerDbEntity>
}