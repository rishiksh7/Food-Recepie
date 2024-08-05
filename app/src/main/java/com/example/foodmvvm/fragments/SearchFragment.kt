package com.example.foodmvvm.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.foodmvvm.R
import com.example.foodmvvm.activities.MainActivity
import com.example.foodmvvm.adapters.MealsAdapter
import com.example.foodmvvm.databinding.FragmentHomeBinding
import com.example.foodmvvm.databinding.FragmentSearchBinding
import com.example.foodmvvm.viewmodels.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchMealsAdapter: MealsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareSearchMealsRecyclerView()

        binding.icSearch.setOnClickListener { searchMeals() }
        observeSearchedMealsLiveData()

        val searchJob : Job?=null
        binding.edSearch.addTextChangedListener {searchQuery->
            searchJob?.cancel()
            lifecycleScope.launch {
                delay(5000)
                viewModel.searchMeal(searchQuery.toString())
            }
        }

    }

    private fun observeSearchedMealsLiveData() {
        viewModel.observeSearchedMealsLiveData().observe(viewLifecycleOwner, Observer {
            searchMealsAdapter.differ.submitList(it)
        })
    }


    private fun searchMeals() {

        val searchQuery= binding.edSearch.text.toString()
        if(searchQuery.isNotEmpty()){
            viewModel.searchMeal(searchQuery)
        }
    }

    private fun prepareSearchMealsRecyclerView() {

        searchMealsAdapter= MealsAdapter()
        binding.rvSearchedMeals.apply {
            layoutManager= GridLayoutManager(context,2, GridLayoutManager.VERTICAL,false)
            adapter=searchMealsAdapter
        }
    }


}