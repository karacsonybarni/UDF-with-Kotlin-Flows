package com.example.beerapp.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Beer::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun beerDao(): BeerDao
}