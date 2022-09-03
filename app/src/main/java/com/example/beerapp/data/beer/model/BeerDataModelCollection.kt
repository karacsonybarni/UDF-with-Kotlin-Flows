package com.example.beerapp.data.beer.model

data class BeerDataModelCollection(val beers: Array<BeerDataModel>) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BeerDataModelCollection

        if (!beers.contentEquals(other.beers)) return false

        return true
    }

    override fun hashCode(): Int {
        return beers.contentHashCode()
    }
}