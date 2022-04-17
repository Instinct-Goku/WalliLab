package com.skdevelopment.wallpaperapp.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skdevelopment.wallpaperapp.R
import com.skdevelopment.wallpaperapp.Views.Wallpapers


class WallpaperAdapter(private val listener : itemClicked) : RecyclerView.Adapter<WallpaperAdapter.WallpaperViewHolder>() {

    class WallpaperViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.item_img)
    }

    val items: ArrayList<Wallpapers> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_view, parent, false)

        val viewHolder = WallpaperViewHolder(view)

        view.setOnClickListener {
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: WallpaperViewHolder, position: Int) {
        val curItem = items[position]
        Glide.with(holder.itemView.context).load(curItem.imgUrl).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateWallpapers(updatedWall: ArrayList<Wallpapers>) {
        items.clear()
        items.addAll(updatedWall)

        notifyDataSetChanged()
    }



}


interface itemClicked {
    fun onItemClicked(item: Wallpapers)
}