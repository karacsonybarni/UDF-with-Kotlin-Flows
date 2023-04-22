package com.example.beerapp.ui.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.beerapp.ui.model.Beer

class PagerAdapter(fragment: PagerFragment) : FragmentStateAdapter(fragment) {

    private var beerMap = fragment.pagerViewModel.beerMap
    private var beerArray = ArrayList<Beer>(beerMap.values)

    fun addBeer(beer: Beer) {
        beerArray.add(beer)
        notifyItemInserted(beerArray.size - 1)
    }

    fun clear() {
        beerArray.clear()
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