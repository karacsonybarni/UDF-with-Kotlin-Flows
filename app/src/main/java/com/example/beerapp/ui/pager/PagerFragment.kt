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
import androidx.viewpager2.widget.ViewPager2
import com.example.beerapp.databinding.FragmentPagerBinding
import kotlinx.coroutines.launch

class PagerFragment : Fragment() {

    val viewModel: PagerViewModel by activityViewModels()

    private lateinit var binding: FragmentPagerBinding
    private lateinit var adapter: PagerAdapter
    private lateinit var pager: ViewPager2

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
        collectFlows()
    }

    private fun setupPager() {
        adapter = PagerAdapter(this)

        pager = binding.pager
        pager.adapter = adapter
        pager.isUserInputEnabled = false

        initCurrentItemWithoutScroll()
    }

    private fun initCurrentItemWithoutScroll() {
        viewModel.currentItemIndexFlow.value?.let { currentItem ->
            pager.setCurrentItem(currentItem, false)
        }
    }

    private fun collectFlows() {
        val lifecycleScope = viewLifecycleOwner.lifecycleScope
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    collectBeersFlow()
                }
                launch {
                    collectCurrentItemIndexFlow()
                }
                launch {
                    collectAllBeersFlow()
                }
            }
        }
    }

    private suspend fun collectBeersFlow() {
        viewModel.beerFlow.collect { beer ->
            adapter.addBeer(beer)
        }
    }

    private suspend fun collectCurrentItemIndexFlow() {
        viewModel.currentItemIndexFlow.collect { currentItemIndex ->
            currentItemIndex?.let { pager.currentItem = it }
        }
    }

    private suspend fun collectAllBeersFlow() {
        viewModel.allBeersFlow.collect {
            if (it.isEmpty()) {
                adapter.clear()
            }
        }
    }
}