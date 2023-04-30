package com.example.beerapp.ui.pager

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.beerapp.ui.model.Beer

class PagerAdapter(
    fragment: PagerFragment,
    beers: Collection<Beer>
) : FragmentStateAdapter(fragment) {

    private var beerArray = ArrayList<Beer>(beers)

    var beerMap: Map<Long, Beer> = emptyMap()
        set(value) {
            field = value
            if (value.isEmpty()) {
                clear()
            }
        }

    @SuppressLint("NotifyDataSetChanged")
    private fun clear() {
        beerArray.clear()
        notifyDataSetChanged()
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