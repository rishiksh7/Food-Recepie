package com.example.foodmvvm.retrofit


import com.example.foodmvvm.pojo.Category
import com.example.foodmvvm.pojo.CategoryList
import com.example.foodmvvm.pojo.MealsByCategoryList
import com.example.foodmvvm.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal() : Call<MealList>

    @GET("lookup.php?")
    fun getMealDetails(@Query("1") id:String):Call<MealList>

    @GET("filter.php?")
    fun getPopularMeals(@Query("c") categoryName: String) : Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategoryList() : Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName: String): Call<MealsByCategoryList>

    @GET("search.php")
    fun searchMeals(@Query("s") searchQuery: String) : Call<MealList>
}