package com.example.beerapp.ui.pager

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.beerapp.ui.model.Beer

class PagerAdapter(private val fragment: PagerFragment) : FragmentStateAdapter(fragment) {

    private val beerMap: Map<Long, Beer>
        get() = fragment.beerMap

    private var beerArray = ArrayList<Beer>(beerMap.values)

    @SuppressLint("NotifyDataSetChanged")
    fun reset() {
        if (beerArray.isNotEmpty()) {
            beerArray.clear()
            notifyDataSetChanged()
        }
    }

    fun addBeer(beer: Beer) {
        beerArray.add(beer)
        notifyItemInserted(beerArray.size - 1)
    }

    override fun getItemCount() = beerArray.size

    override fun createFragment(position: Int): Fragment {
        val id = beerArray[position].id
        return BeerFragment.newInstance(id)
    }

    override fun getItemId(position: Int) = beerArray[position].id

    override fun containsItem(itemId: Long) = beerMap.containsKey(itemId)
}