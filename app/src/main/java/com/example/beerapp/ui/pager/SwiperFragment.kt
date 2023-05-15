package com.example.beerapp.ui.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.beerapp.ui.main.MainActivity

class SwiperFragment : Fragment() {

    private val viewModel: PagerViewModel by activityViewModels()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val uiState = viewModel.uiStateFlow.collectAsStateWithLifecycle()
                Scaffold {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (uiState.value.beerMap.isNotEmpty()) {
                            CardStack(
                                items = uiState.value.beerMap.values.toList(),
                                onSwipeLeft = {},
                                onSwipeRight = { beer -> viewModel.like(beer) },
                                onEmptyStack = {
                                    (activity as MainActivity).navigateToLikedBeerList()
                                }
                            )
                        } else {
                            Text(text = "No cards", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}