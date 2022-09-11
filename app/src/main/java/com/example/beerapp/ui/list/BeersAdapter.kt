package com.example.beerapp.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.beerapp.databinding.ItemBeerBinding
import com.example.beerapp.ui.model.Beer
import com.squareup.picasso.Picasso

class BeersAdapter : RecyclerView.Adapter<BeerViewHolder>() {

    private var beerArray: Array<Beer>? = null

    var beers: Map<Long, Beer>? = null
        set(beerMap) {
            field = beerMap
            beerArray = beers
                ?.entries
                ?.map { it.value }
                ?.toTypedArray()
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val binding = ItemBeerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BeerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        val beer = beerArray?.get(position) ?: return

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

    override fun getItemCount() = beerArray?.size ?: 0
}