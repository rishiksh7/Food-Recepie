package com.example.foodmvvm.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodmvvm.db.MealDatabase
import com.example.foodmvvm.pojo.Meal
import com.example.foodmvvm.pojo.MealList
import com.example.foodmvvm.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(
    val mealDatabase: MealDatabase
): ViewModel() {

    private var MealDetailsLiveData= MutableLiveData<Meal>()

    fun getMealDetails(id:String){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {

                if(response.body()!=null){
                    MealDetailsLiveData.value=response.body()!!.meals[0]

                } else {
                    return
                }

            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Meal Activity",t.message.toString())
            }

        })
    }


    fun observeMealDetailsLiveData(): LiveData<Meal> {
        return MealDetailsLiveData
    }


    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }



}