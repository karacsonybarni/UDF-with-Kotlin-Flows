package com.example.beerapp.ui.pager

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.beerapp.R
import com.example.beerapp.ui.MainState
import com.example.beerapp.ui.MainViewModel
import com.example.beerapp.ui.list.ListActivity
import kotlinx.coroutines.launch

class PagerActivity : AppCompatActivity() {

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
                    if (MainState.LikedBeersList == it) {
                        val startListActivityIntent =
                            Intent(this@PagerActivity, ListActivity::class.java)
                        startActivity(startListActivityIntent)
                    }
                }
            }
        }
    }
}