package com.example.beerapp.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.beerapp.databinding.ItemBeerBinding
import com.example.beerapp.ui.model.Beer
import com.squareup.picasso.Picasso

class BeersAdapter : RecyclerView.Adapter<BeerViewHolder>() {

    var beers = emptyList<Beer>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val binding = ItemBeerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BeerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        val beer = beers[position]
        val binding = holder.binding
        binding.name.text = beer.name
        beer.imageUrl?.let { loadImage(it, binding.image) }
    }

    private fun loadImage(url: String, imageView: ImageView) {
        Picasso
            .get()
            .load(url)
            .resize(800, 0)
            .onlyScaleDown()
            .into(imageView)
    }

    override fun getItemCount() = beers.size
}