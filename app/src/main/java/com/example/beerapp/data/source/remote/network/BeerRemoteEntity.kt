package com.example.beerapp.data.source.remote.network

import com.squareup.moshi.Json

data class BeerRemoteEntity(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "tagline") val tagline: String,
    @Json(name = "image_url") val imageUrl: String?
)