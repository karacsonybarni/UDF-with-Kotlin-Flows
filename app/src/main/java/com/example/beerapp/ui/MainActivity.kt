package com.example.beerapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.beerapp.R
import com.example.beerapp.ui.list.ListFragment
import com.example.beerapp.ui.pager.PagerFragment
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        collectFlows()
    }

    private fun collectFlows() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mainStateFlow.collect {
                    when (it) {
                        MainState.BeersPager ->
                            showFragment(PagerFragment.tag) { PagerFragment.newInstance() }
                        MainState.LikedBeersList ->
                            showFragment(ListFragment.tag) { ListFragment.newInstance() }
                    }
                }
            }
        }
    }

    private fun showFragment(tag: String, initFragment: () -> Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, getOrCreateFragment(tag, initFragment), tag)
            .commitNow()
    }

    private fun getOrCreateFragment(tag: String, initFragment: () -> Fragment): Fragment {
        var fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment == null) {
            fragment = initFragment()
        }
        return fragment
    }
}