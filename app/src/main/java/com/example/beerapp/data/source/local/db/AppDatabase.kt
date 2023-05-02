package com.example.beerapp.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.beerapp.data.source.local.db.beer.BeerDao
import com.example.beerapp.data.source.local.db.beer.BeerDbEntity
import com.example.beerapp.data.source.local.db.beer.Converters

@Database(entities = [BeerDbEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun beerDao(): BeerDao
}