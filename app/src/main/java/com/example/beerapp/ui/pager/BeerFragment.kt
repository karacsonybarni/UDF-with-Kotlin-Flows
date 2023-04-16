package com.example.beerapp.ui.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.beerapp.databinding.FragmentBeerBinding
import com.example.beerapp.ui.MainViewModel
import com.example.beerapp.ui.model.Beer
import com.squareup.picasso.Picasso

private const val ARG_ID = "position"

class BeerFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(id: Long) =
            BeerFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_ID, id)
                }
            }
    }

    private val pagerViewModel: PagerViewModel by viewModels({ requireParentFragment() })
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var beer: Beer
    private lateinit var binding: FragmentBeerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val id = it.getLong(ARG_ID)
            beer = pagerViewModel.getBeer(id)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBeerBinding.inflate(inflater, container, false)
        bindData()
        return binding.root
    }

    private fun bindData() {
        beer.imageUrl?.let { loadImage(it) }
        binding.name.text = beer.name
        binding.tagline.text = beer.tagline
        binding.likeButton.setOnClickListener {
            it.isEnabled = false
            pagerViewModel.like(beer)
            pageToNextBeerOrEndPager()
        }
        binding.dislikeButton.setOnClickListener {
            it.isEnabled = false
            pageToNextBeerOrEndPager()
        }
    }

    private fun pageToNextBeerOrEndPager() {
        if (pagerViewModel.hasNextBeer) {
            pagerViewModel.pageToNextBeer()
        } else {
            mainViewModel.navigateToLikedBeerList()
        }
    }

    private fun loadImage(url: String) {
        Picasso
            .get()
            .load(url)
            .resize(2048, 0)
            .onlyScaleDown()
            .into(binding.image)
    }
}