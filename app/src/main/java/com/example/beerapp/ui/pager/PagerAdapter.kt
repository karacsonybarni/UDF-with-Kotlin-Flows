package com.example.beerapp.ui.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.beerapp.ui.model.Beer

class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private var beerMap = emptyMap<Long, Beer>()
    private var beerArray = emptyArray<Beer>()

    fun updateValues(beers: Map<Long, Beer>) {
        beerMap = beers
        beerArray = beers.entries.map { it.value }.toTypedArray()
        notifyDataSetChanged()
    }

    override fun getItemCount() = beerArray.size

    override fun createFragment(position: Int): Fragment {
        val id = beerArray[position].id
        return BeerFragment.newInstance(id)
    }

    override fun getItemId(position: Int): Long {
        return beerArray[position].id
    }

    override fun containsItem(itemId: Long): Boolean {
        return beerMap.contains(itemId)
    }
}