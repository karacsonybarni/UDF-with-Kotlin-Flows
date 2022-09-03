package com.example.beerapp.data.beer.network

import com.example.beerapp.data.beer.network.entity.BeerEntity
import retrofit2.http.GET

interface BeersApiService {

    @GET("beers/random")
    suspend fun getRandomBeer(): Array<BeerEntity>
}