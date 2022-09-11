package com.example.beerapp.ui.util

import com.example.beerapp.data.beer.model.BeerDataModel
import com.example.beerapp.ui.model.Beer

object ModelTransformationUtil {

    fun toBeer(beer: BeerDataModel): Beer {
        return Beer(beer.id, beer.name, beer.tagLine, beer.imageUrl)
    }
}