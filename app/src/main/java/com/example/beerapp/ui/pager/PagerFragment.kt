package com.example.beerapp.ui.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.beerapp.databinding.FragmentPagerBinding
import kotlinx.coroutines.launch

class PagerFragment : Fragment() {

    companion object {
        fun newInstance() = PagerFragment()
    }

    private val viewModel: PagerViewModel by activityViewModels()

    private lateinit var binding: FragmentPagerBinding
    private lateinit var adapter: PagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPager()
        updateBeers()
    }

    private fun setupPager() {
        adapter = PagerAdapter(this)

        val pager = binding.pager
        pager.adapter = adapter
        pager.isUserInputEnabled = false
    }

    private fun updateBeers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.beersFlow.collect { beers ->
                    adapter.notifyDataSetChanged(beers.size)
                }
            }
        }
    }
}