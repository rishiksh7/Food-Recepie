package com.example.foodmvvm.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodmvvm.R
import com.example.foodmvvm.activities.CategoryMealsActivity
import com.example.foodmvvm.activities.MainActivity
import com.example.foodmvvm.activities.MealActivity
import com.example.foodmvvm.adapters.CategoryListAdapter
import com.example.foodmvvm.adapters.PopularMealAdapter
import com.example.foodmvvm.databinding.FragmentHomeBinding
import com.example.foodmvvm.fragments.bottomsheet.MealBottomSheetFragment
import com.example.foodmvvm.pojo.MealsByCategory
import com.example.foodmvvm.pojo.Meal
import com.example.foodmvvm.viewmodels.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal : Meal
    private lateinit var populareMealAdapter: PopularMealAdapter
    private lateinit var categoryListAdapter: CategoryListAdapter

    companion object {
        const val MEAL_ID = "com.example.foodmvvm.fragments.idMeal"
        const val MEAL_NAME = "com.example.foodmvvm.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.foodmvvm.fragments.thumbMeal"
        const val CATEGORY_NAME="com.example.foodmvvm.fragments.categoryName"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
        populareMealAdapter= PopularMealAdapter()
        categoryListAdapter=CategoryListAdapter()

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularMealRecyclerView()
        prepareCategoryListRecyclerView()

        viewModel.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        viewModel.getPopularMeals()
        observerPopularMeal()
        onPopularMealClick()

        viewModel.getCategoryList()
        observerCategoryList()

        onCategoryClick()

        onPopularMealLongClick()

        onSearchClick()

    }

    private fun onSearchClick() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun onCategoryClick() {
        categoryListAdapter.onItemClick={category ->
            val intent=Intent(activity,CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)

        }
    }

    private fun prepareCategoryListRecyclerView() {
        binding.rvCategory.apply {
            layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter=categoryListAdapter
        }
    }

    private fun observerCategoryList() {
        viewModel.observeCategoryListLiveData().observe(viewLifecycleOwner, Observer { categories->
                categoryListAdapter.setCategoryList(categories)
        })
    }

    private fun onPopularMealClick() {
        populareMealAdapter.onItemClick={ meal->
            val intent=Intent(activity,MealActivity::class.java)
                intent.putExtra(MEAL_ID,meal.idMeal)
                intent.putExtra(MEAL_NAME,meal.strMeal)
                intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun onPopularMealLongClick(){
        populareMealAdapter.onItemLongClick= {
            val mealBottomSheetFragment = MealBottomSheetFragment.newInstance(it.idMeal)
            mealBottomSheetFragment.show(childFragmentManager,"Meal Info")


        }
    }

    private fun preparePopularMealRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter=populareMealAdapter
        }
    }

    private fun observerPopularMeal() {
        viewModel.observePopularMealLiveData().observe(viewLifecycleOwner
        ) { mealList->

            populareMealAdapter.setMeals(mealList as ArrayList<MealsByCategory>)
        }


    }

    private fun onRandomMealClick() {
        binding.randomMeal.setOnClickListener {
            val intent= Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal.strMealThumb)
                .into(binding.imgRandomMeal)

            this.randomMeal=meal
        }
    }


}