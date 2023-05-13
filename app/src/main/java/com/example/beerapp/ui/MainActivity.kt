package com.example.beerapp.ui

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.beerapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val navController: NavController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController
    }

    private val onBackPressedCallback: OnBackPressedCallback by lazy {
        onBackPressedDispatcher.addCallback(this) {
            viewModel.onNavigateToBeerPager()
            navController.navigateUp()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.initialize()
        collectAppStateFlow()
    }

    private fun collectAppStateFlow() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.appStateFlow.collect {
                    bindUiState()
                }
            }
        }
    }

    private fun bindUiState() {
        supportActionBar?.setDisplayHomeAsUpEnabled(viewModel.isDisplayHomeAsUpEnabled)
        onBackPressedCallback.isEnabled = viewModel.isDisplayHomeAsUpEnabled
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedCallback.handleOnBackPressed()
        return true
    }

    fun navigateToLikedBeerList() {
        viewModel.onNavigateToLikedBeerList()
        navController.navigate(R.id.action_pagerFragment_to_listFragment)
    }
}