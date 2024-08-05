package com.example.foodmvvm.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodmvvm.R
import com.example.foodmvvm.adapters.CategoryMealsAdapter
import com.example.foodmvvm.databinding.ActivityCategoryMealsBinding
import com.example.foodmvvm.fragments.HomeFragment
import com.example.foodmvvm.pojo.MealsByCategory
import com.example.foodmvvm.viewmodels.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {

    lateinit var binding: ActivityCategoryMealsBinding
    lateinit var categoryMealsViewModel: CategoryMealsViewModel
    lateinit var categoryMealsAdapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        prepareRecyclerView()


        categoryMealsViewModel= ViewModelProvider(this)[CategoryMealsViewModel::class.java]

        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        categoryMealsViewModel.observeMealsLiveData().observe(this, Observer { mealsList->
            val mealListCount=mealsList.size
            val categoryName=intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!
            binding.tvCategoryCount.text="Total $mealListCount Recipes for $categoryName"
            categoryMealsAdapter.setMealsList(mealsList as ArrayList<MealsByCategory>)
        })



    }

    private fun prepareRecyclerView() {
        categoryMealsAdapter= CategoryMealsAdapter()
        binding.mealRecyclerview.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=categoryMealsAdapter
        }
    }
}