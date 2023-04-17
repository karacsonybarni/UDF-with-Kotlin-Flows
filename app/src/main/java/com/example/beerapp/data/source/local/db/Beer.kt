package com.example.beerapp.data.source.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Beer(
    @PrimaryKey val id: Long,
    val name: String,
    val tagline: String,
    val imageUrl: String?
)