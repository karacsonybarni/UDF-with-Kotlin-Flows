package com.example.beerapp.ui.pager

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.beerapp.ui.model.Beer

class PagerAdapter(
    fragment: PagerFragment,
    beerMap: Map<Long, Beer>
) : FragmentStateAdapter(fragment) {

    private var beerArray = ArrayList(beerMap.values)

    var beerMap = beerMap
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            val sizeDiff = value.size - field.size

            field = value
            beerArray = ArrayList<Beer>(value.values)

            if (value.isNotEmpty()) {
                notifyItemRangeInserted(value.size - sizeDiff, sizeDiff)
            } else {
                notifyDataSetChanged()
            }
        }

    override fun getItemCount() = beerArray.size

    override fun createFragment(position: Int): Fragment {
        val id = beerArray[position].id
        return BeerFragment.newInstance(id)
    }

    override fun getItemId(position: Int) = beerArray[position].id

    override fun containsItem(itemId: Long) = beerMap.containsKey(itemId)
}