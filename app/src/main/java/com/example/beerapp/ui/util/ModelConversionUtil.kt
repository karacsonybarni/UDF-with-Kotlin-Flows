package com.example.beerapp.ui.util

import com.example.beerapp.data.model.BeerDataModel
import com.example.beerapp.ui.model.Beer

object ModelConversionUtil {

    fun BeerDataModel.toBeer() =
        Beer(
            id = id,
            name = name,
            tagline = tagline,
            imageUrl = imageUrl
        )

    fun Beer.toBeerDataModel() =
        BeerDataModel(
            id = id,
            name = name,
            tagline = tagline,
            imageUrl = imageUrl
        )
}