package com.example.beerapp.data.source.local.db.beer

import androidx.room.TypeConverter
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(millis: Long) = Date(millis)

    @TypeConverter
    fun dateToTimestamp(date: Date): Long = date.time
}