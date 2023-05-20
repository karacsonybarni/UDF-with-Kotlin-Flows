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
import com.example.beerapp.ui.swiper.SwiperViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PagerFragment : Fragment() {

    private val viewModel: SwiperViewModel by activityViewModels()

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
        collectFlowInLifecycleScope()
    }

    private fun setupPager() {
        adapter = PagerAdapter(this, viewModel.uiState.beerMap)

        pager = binding.pager
        pager.adapter = adapter
        pager.isUserInputEnabled = false

        initCurrentItemWithoutScroll()
    }

    private fun initCurrentItemWithoutScroll() {
        viewModel.uiState.currentItemIndex?.let { currentItem ->
            pager.setCurrentItem(currentItem, false)
        }
    }

    private fun collectFlowInLifecycleScope() =
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                collectUiStateFlow()
            }
        }

    private suspend fun collectUiStateFlow() {
        viewModel.uiStateFlow.collect { uiState ->
            adapter.beerMap = uiState.beerMap
            uiState.currentItemIndex?.let { pager.currentItem = it }
        }
    }
}