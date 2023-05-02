package com.example.beerapp.ui.pager

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.beerapp.ui.model.Beer

class PagerAdapter(
    fragment: PagerFragment,
    beerMap: Map<Long, Beer>
) : FragmentStateAdapter(fragment) {

    private val beerIdSet: HashSet<Long> = HashSet(beerMap.keys)
    private var beerArray = ArrayList<Beer>(beerMap.values)

    @SuppressLint("NotifyDataSetChanged")
    fun reset() {
        if (beerArray.isNotEmpty()) {
            beerIdSet.clear()
            beerArray.clear()
            notifyDataSetChanged()
        }
    }

    fun addBeer(beer: Beer) {
        beerIdSet.add(beer.id)
        beerArray.add(beer)
        notifyItemInserted(beerArray.size - 1)
    }

    override fun getItemCount() = beerArray.size

    override fun createFragment(position: Int): Fragment {
        val id = beerArray[position].id
        return BeerFragment.newInstance(id)
    }

    override fun getItemId(position: Int) = beerArray[position].id

    override fun containsItem(itemId: Long) = beerIdSet.contains(itemId)
}