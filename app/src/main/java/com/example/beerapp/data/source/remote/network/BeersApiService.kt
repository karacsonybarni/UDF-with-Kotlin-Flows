package com.example.beerapp.data.source.remote.network

import retrofit2.http.GET

interface BeersApiService {

    @GET("beers/random")
    suspend fun getRandomBeer(): Array<BeerRemoteEntity>
}