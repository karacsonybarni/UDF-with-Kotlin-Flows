package com.example.beerapp.ui.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.beerapp.data.beer.model.BeerCollection
import com.example.beerapp.ui.beer.BeerFragment

class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    var beerCollection: BeerCollection? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = beerCollection?.beers?.size ?: 0

    override fun createFragment(position: Int): Fragment {
        val beer = beerCollection?.beers?.get(position)
        return BeerFragment.newInstance(beer?.name ?: "No name beer")
    }
}