package com.skdevelopment.wallpaperapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skdevelopment.wallpaperapp.Views.Category


class CategoryAdapter(private val listener : itemClicked) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.item_img)
    }

    val items: ArrayList<Category> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_view, parent, false)

        val viewHolder = CategoryViewHolder(view)

        view.setOnClickListener {
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val curItem = items[position]
        Glide.with(holder.itemView.context).load(curItem.imgUrl).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCategory(updatedCat: ArrayList<Category>) {
        items.clear()
        items.addAll(updatedCat)

        notifyDataSetChanged()
    }



}

interface itemClicked {
    fun onItemClicked(item : Category)
}