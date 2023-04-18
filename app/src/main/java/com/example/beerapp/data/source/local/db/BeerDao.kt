package com.example.beerapp.data.source.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface BeerDao {

    @Query("SELECT * FROM beer")
    fun getAll(): List<BeerDbEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(beerDbEntities: Collection<BeerDbEntity>)

    @Query("DELETE FROM beer")
    fun deleteAll()

    @Update
    fun update(beer: BeerDbEntity)

    @Query("SELECT * FROM beer WHERE isLiked = 1")
    fun selectLiked(): List<BeerDbEntity>
}