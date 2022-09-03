package com.example.beerapp.data.beer.network.entity

import com.squareup.moshi.Json

data class BeerEntity(@Json(name = "name") val name: String)