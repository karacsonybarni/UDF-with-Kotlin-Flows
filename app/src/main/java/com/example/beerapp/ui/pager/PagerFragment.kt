package com.example.beerapp.ui.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.example.beerapp.databinding.FragmentPagerBinding
import kotlinx.coroutines.launch

class PagerFragment : Fragment() {

    private val pagerViewModel: PagerViewModel by viewModels()

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
    }

    private fun collectFlows() {
        val lifecycleScope = viewLifecycleOwner.lifecycleScope
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                pagerViewModel.beersFlow.collect { beers ->
                    adapter.updateValues(beers)
                    pagerViewModel.currentItemIndexFlow.value?.let { currentItem ->
                        pager.setCurrentItem(currentItem, false)
                    }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                pagerViewModel.currentItemIndexFlow.collect { currentItem ->
                    if (currentItem != null && currentItem < adapter.itemCount) {
                        pager.currentItem = currentItem
                    }
                }
            }
        }
    }
}