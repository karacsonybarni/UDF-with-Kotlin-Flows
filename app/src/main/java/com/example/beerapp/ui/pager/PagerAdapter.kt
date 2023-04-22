package com.example.beerapp.ui.pager

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.beerapp.ui.model.Beer

class PagerAdapter(fragment: PagerFragment) : FragmentStateAdapter(fragment) {

    private var viewModel = fragment.viewModel
    private var beerArray = ArrayList<Beer>(viewModel.beers)

    fun addBeer(beer: Beer) {
        beerArray.add(beer)
        notifyItemInserted(beerArray.size - 1)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        beerArray.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount() = beerArray.size

    override fun createFragment(position: Int): Fragment {
        val id = beerArray[position].id
        return BeerFragment.newInstance(id)
    }

    override fun getItemId(position: Int) = beerArray[position].id

    override fun containsItem(itemId: Long) = viewModel.hasBeer(itemId)
}