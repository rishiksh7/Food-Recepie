package com.example.foodmvvm.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodmvvm.activities.MainActivity
import com.example.foodmvvm.adapters.CategoryListAdapter
import com.example.foodmvvm.databinding.FragmentCategoriesBinding
import com.example.foodmvvm.viewmodels.HomeViewModel

class CategoriesFragment : Fragment() {

    private lateinit var binding:FragmentCategoriesBinding
    private lateinit var categoryAdapter: CategoryListAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCategoriesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareCategoryRecyclerView()
        observeCategories()
    }

    private fun observeCategories() {
        viewModel.observeCategoryListLiveData().observe(viewLifecycleOwner,Observer { categories->
            categoryAdapter.setCategoryList(categories)

        })
    }

    private fun prepareCategoryRecyclerView() {
        categoryAdapter = CategoryListAdapter()
        binding.rvCategory.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter=categoryAdapter
        }
    }


}