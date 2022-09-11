package com.example.beerapp.data.beer.network.entity

import com.squareup.moshi.Json

data class BeerEntity(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "tagline") val tagline: String,
    @Json(name = "image_url") val imageUrl: String?
)