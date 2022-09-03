package com.example.beerapp.ui.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.beerapp.databinding.FragmentBeerBinding
import com.example.beerapp.ui.model.Beer

private const val ARG_POSITION = "position"

class BeerFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(position: Int) =
            BeerFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_POSITION, position)
                }
            }
    }

    private val viewModel: PagerViewModel by activityViewModels()
    private lateinit var beer: Beer
    private lateinit var binding: FragmentBeerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val position = it.getInt(ARG_POSITION)
            beer = viewModel.beers[position]
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
        binding.name.text = beer.name
        binding.likeButton.setOnClickListener {
            it.isEnabled = false
            beer.likeAction()
        }
        binding.dislikeButton.setOnClickListener {
            it.isEnabled = false
            beer.dislikeAction()
        }
    }
}