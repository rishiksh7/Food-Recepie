package com.example.foodmvvm.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodmvvm.R
import com.example.foodmvvm.databinding.ActivityMealBinding
import com.example.foodmvvm.db.MealDatabase
import com.example.foodmvvm.fragments.HomeFragment
import com.example.foodmvvm.pojo.Meal
import com.example.foodmvvm.viewmodels.MealViewModel
import com.example.foodmvvm.viewmodels.MealViewModelFactory

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var youtubeLink:String
    private lateinit var mealVM : MealViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory= MealViewModelFactory(mealDatabase)

        mealVM=ViewModelProvider(this,viewModelFactory)[MealViewModel::class.java]

        getMealInfoFromIntent()
        setMealInfoToViews()

        loadingMeal()

        mealVM.getMealDetails(mealId)
        observeMealDetailsLiveData()

        onYoutubeImageClick()
        onFavClick()

    }

    private fun onFavClick() {
        binding.btnFav.setOnClickListener {
            mealToSave?.let {
                mealVM.insertMeal(it)
                Toast.makeText(this,"Meal Inserted in DB",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onYoutubeImageClick(){
        binding.imgYoutube.setOnClickListener {
            val intent=Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    var mealToSave: Meal?=null
    private fun observeMealDetailsLiveData() {
        mealVM.observeMealDetailsLiveData().observe(this
        ) { value ->

            MealLoaded()
            val meal = value
            mealToSave=meal

            binding.apply {
                tvArea.text = "Area : ${meal.strArea}"
                tvCategory.text = "Category : ${meal.strCategory}"
                tvInstructionsText.text = meal.strInstructions
                youtubeLink= meal.strYoutube.toString()
            }
        }
    }

    private fun setMealInfoToViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title=mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInfoFromIntent() {
        val intent=intent
        mealId=intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName=intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb=intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingMeal(){
        binding.apply {
            progressBar.visibility= View.VISIBLE
            tvInstructions.visibility=View.INVISIBLE
            tvCategory.visibility=View.INVISIBLE
            tvArea.visibility=View.INVISIBLE
            imgYoutube.visibility=View.INVISIBLE
            btnFav.visibility=View.INVISIBLE
        }
    }

    private fun MealLoaded(){
        binding.apply {
            progressBar.visibility= View.INVISIBLE
            tvInstructions.visibility=View.VISIBLE
            tvCategory.visibility=View.VISIBLE
            tvArea.visibility=View.VISIBLE
            imgYoutube.visibility=View.VISIBLE
            btnFav.visibility=View.VISIBLE
        }
    }
}