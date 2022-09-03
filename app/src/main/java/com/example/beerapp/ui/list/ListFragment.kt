package com.example.beerapp.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beerapp.databinding.FragmentListBinding
import kotlinx.coroutines.launch


class ListFragment : Fragment() {

    companion object {
        const val tag = "ListFragment"
        fun newInstance() = ListFragment()
    }

    private val viewModel: ListViewModel by viewModels()

    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: BeersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        updateData()
    }

    private fun setupRecyclerView() {
        adapter = BeersAdapter()

        val layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.adapter = adapter
    }

    private fun updateData() {
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.beers = viewModel.getLikedBeers()
        }
    }
}