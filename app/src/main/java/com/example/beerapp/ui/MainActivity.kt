package com.example.beerapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.beerapp.R
import com.example.beerapp.ui.list.BeerListFragment
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
                    if (AppState.BeersPager == it) {
                        supportFragmentManager.commit {
                            setReorderingAllowed(true)
                            replace<PagerFragment>(R.id.container)
                        }
                    } else {
                        supportFragmentManager.commit {
                            setReorderingAllowed(true)
                            replace<BeerListFragment>(R.id.container)
                        }
                    }
                }
            }
        }
    }
}