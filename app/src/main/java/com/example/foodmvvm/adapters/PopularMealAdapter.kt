package com.example.foodmvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodmvvm.databinding.PopularMealBinding
import com.example.foodmvvm.pojo.MealsByCategory


class PopularMealAdapter():RecyclerView.Adapter<PopularMealAdapter.PopularMealViewHolder>() {

    private var mealsList= ArrayList<MealsByCategory>()
    lateinit var onItemClick: ((MealsByCategory)->Unit)
    var onItemLongClick: ((MealsByCategory)->Unit)?=null

    fun setMeals(mealList: ArrayList<MealsByCategory>){
        this.mealsList=mealList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularMealBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgPopularMeal)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])
        }

        holder.itemView.setOnLongClickListener {
            onItemLongClick?.invoke(mealsList[position])
            true
        }
    }

    class PopularMealViewHolder(var binding:PopularMealBinding):RecyclerView.ViewHolder(binding.root)

}