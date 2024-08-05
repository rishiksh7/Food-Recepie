package com.example.foodmvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodmvvm.databinding.CategoryItemBinding
import com.example.foodmvvm.pojo.Category


class CategoryListAdapter(): RecyclerView.Adapter<CategoryListAdapter.CategoryListViewHolder>() {

    private var categoryList = ArrayList<Category>()
    lateinit var onItemClick : ((Category)->Unit)

    fun setCategoryList(categoryList: List<Category>){
        this.categoryList=categoryList as ArrayList<Category>
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListViewHolder {
        return CategoryListViewHolder(
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryListViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoryList[position].strCategoryThumb)
            .into(holder.binding.imgCategory)

        holder.binding.tvCategoryName.text=categoryList[position].strCategory
        holder.itemView.setOnClickListener {
            onItemClick.invoke(categoryList[position])
        }


    }


    inner class CategoryListViewHolder(var binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root)

}