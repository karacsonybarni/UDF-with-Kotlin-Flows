package com.example.beerapp.ui.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private var size = 0

    override fun getItemCount() = size

    override fun createFragment(position: Int): Fragment {
        return BeerFragment.newInstance(position)
    }

    fun notifyDataSetChanged(size: Int) {
        this.size = size
        notifyDataSetChanged()
    }
}