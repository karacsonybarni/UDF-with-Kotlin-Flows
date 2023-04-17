package com.example.beerapp.data.source.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BeerDao {

    @Query("SELECT * FROM beer")
    fun getAll(): Flow<List<Beer>>

    @Insert
    fun insertAll(vararg beers: Beer)
}