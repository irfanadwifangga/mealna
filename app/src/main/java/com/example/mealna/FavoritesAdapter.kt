package com.example.mealna

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mealna.databinding.ItemFavoriteBinding

class FavoritesAdapter(
    private val items: List<FavoriteItem>
) : RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FavoriteItem) {
            binding.tvFoodName.text = item.name
            binding.tvFoodCategory.text = item.category

            // TODO: Load image with Glide or another image loading library
            // For example: Glide.with(itemView.context).load(item.imageUrl).into(binding.ivFoodImage)
        }
    }
}
