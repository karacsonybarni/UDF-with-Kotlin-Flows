package com.example.beerapp.data.source.local.db.currentitemindex

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_item")
data class CurrentItemDbEntity(
    @PrimaryKey val id: Int = 0,
    val index: Int?
)