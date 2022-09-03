package com.example.beerapp.ui.list

import androidx.recyclerview.widget.RecyclerView
import com.example.beerapp.databinding.ItemBeerBinding
import com.example.beerapp.ui.model.Beer

class BeerViewHolder(private val binding: ItemBeerBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(beer: Beer) {
        binding.name.text = beer.name
    }
}