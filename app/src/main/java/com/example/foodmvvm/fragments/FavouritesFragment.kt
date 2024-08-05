package com.example.foodmvvm.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.foodmvvm.activities.MainActivity
import com.example.foodmvvm.adapters.MealsAdapter
import com.example.foodmvvm.databinding.FragmentFavouritesBinding
import com.example.foodmvvm.viewmodels.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class FavouritesFragment : Fragment() {

    private lateinit var binding:FragmentFavouritesBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var mealsAdapter: MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel=(activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFavouritesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareFavRecyclerView()
        observeFavouriteMeals()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback (
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
                ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Delete swipe meal

                val position=viewHolder.adapterPosition
                viewModel.deleteMeal(mealsAdapter.differ.currentList[position])

                Snackbar.make(requireView(),"Meal Deleted!",Snackbar.LENGTH_SHORT).setAction(
                    "Undo",View.OnClickListener {
                        viewModel.insertMeal(mealsAdapter.differ.currentList[position])
                    }
                ).show()
            }

        }
            
            ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFav)

    }

    private fun prepareFavRecyclerView() {
        mealsAdapter= MealsAdapter()
        binding.rvFav.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=mealsAdapter
        }
    }

    private fun observeFavouriteMeals() {
        viewModel.observeFavouriteMealListLiveData().observe(viewLifecycleOwner, Observer {meals->
                mealsAdapter.differ.submitList(meals)
        })
    }


}