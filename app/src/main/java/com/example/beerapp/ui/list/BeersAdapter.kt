package com.example.beerapp.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.beerapp.databinding.ItemBeerBinding
import com.example.beerapp.ui.model.Beer

class BeersAdapter : RecyclerView.Adapter<BeerViewHolder>() {

    var beers: Array<Beer>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val binding = ItemBeerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BeerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        beers?.let {
            holder.bind(it[position])
        }
    }

    override fun getItemCount() = beers?.size ?: 0
}