package com.example.beerapp.ui.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.example.beerapp.databinding.FragmentPagerBinding
import com.example.beerapp.ui.MainViewModel
import kotlinx.coroutines.launch

class PagerFragment : Fragment() {

    companion object {
        const val tag = "PagerFragment"
        fun newInstance() = PagerFragment()
    }

    private val mainViewModel: MainViewModel by activityViewModels()
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
        setupViewModel()
        setupPager()
        collectFlows()
    }

    private fun setupViewModel() {
        pagerViewModel.onPagerEnd = { mainViewModel.onPagerEnd() }
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
                    adapter.notifyDataSetChanged(beers.size)
                    pagerViewModel.positionFlow.value?.let { pager.currentItem = it }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                pagerViewModel.positionFlow.collect { position ->
                    if (position != null && position < adapter.itemCount) {
                        pager.currentItem = position
                    }
                }
            }
        }
    }
}