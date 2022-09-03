package com.example.beerapp.ui.beer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.beerapp.databinding.FragmentBeerBinding

private const val ARG_NAME = "name"

class BeerFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            BeerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_NAME, param1)
                }
            }
    }

    private var name: String? = null

    private lateinit var binding: FragmentBeerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(ARG_NAME)
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
        binding.name.text = name
    }
}