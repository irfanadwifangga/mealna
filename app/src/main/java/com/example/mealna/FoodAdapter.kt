package com.example.mealna

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mealna.databinding.ItemFoodBinding

class FoodAdapter : ListAdapter<FoodItem, FoodAdapter.FoodViewHolder>(FoodItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class FoodViewHolder(private val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val context = itemView.context
                    val intent = Intent(context, FoodDetailActivity::class.java)
                    intent.putExtra("food_item", getItem(position))
                    context.startActivity(intent)
                }
            }
        }

        fun bind(item: FoodItem) {
            binding.tvFoodTitle.text = item.title
            binding.tvFoodDescription.text = item.description
            binding.tvRating.text = item.rating.toString()
            binding.tvPrice.text = item.price

            // You'll need an image loading library like Glide or Picasso here
            // For now, we'll just set a placeholder

            val favoriteIcon = if (item.isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border
            binding.ivFavorite.setImageResource(favoriteIcon)

            binding.tvCaloriesInfo.text = "${item.calories} Kal"
            binding.tvCarbsInfo.text = "${item.carbs} Karbo"
            binding.tvProteinInfo.text = "${item.protein} Protein"

            binding.ivFavorite.setOnClickListener {
                // Note: This only updates the state in memory. You might need a more robust
                // way to handle state changes, like using a listener to update the source data.
                item.isFavorite = !item.isFavorite
                notifyItemChanged(adapterPosition)
            }
        }
    }
}

class FoodItemDiffCallback : DiffUtil.ItemCallback<FoodItem>() {
    override fun areItemsTheSame(oldItem: FoodItem, newItem: FoodItem): Boolean {
        // Assuming title is a unique identifier
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: FoodItem, newItem: FoodItem): Boolean {
        return oldItem == newItem
    }
}
