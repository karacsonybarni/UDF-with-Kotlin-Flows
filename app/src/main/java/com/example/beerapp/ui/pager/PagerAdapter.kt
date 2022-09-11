package com.example.beerapp.ui.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.beerapp.ui.model.Beer

class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private var beers: Map<Long, Beer> = HashMap()

    fun updateValues(beers: Map<Long, Beer>) {
        this.beers = beers
        notifyDataSetChanged()
    }

    override fun getItemCount() = beers.size

    override fun createFragment(position: Int): Fragment {
        return BeerFragment.newInstance(position)
    }
}