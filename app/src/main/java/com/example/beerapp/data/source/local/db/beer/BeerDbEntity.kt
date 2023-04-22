package com.example.beerapp.data.source.local.db.beer

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "beer")
data class BeerDbEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val tagline: String,
    val imageUrl: String?,
    val isLiked: Boolean,
    val time: Date
)