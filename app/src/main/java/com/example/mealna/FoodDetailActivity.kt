package com.example.mealna

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.mealna.databinding.ActivityFoodDetailBinding

class FoodDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        val foodItem = intent.getParcelableExtra<FoodItem>("food_item")

        if (foodItem != null) {
            populateViews(foodItem)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false) // Hide the default title
    }

    private fun populateViews(foodItem: FoodItem) {
        // Load image using a library like Glide or Picasso
        // Glide.with(this).load(foodItem.imageUrl).into(binding.ivFoodDetailImage)

        binding.tvFoodDetailTitle.text = foodItem.title
        binding.tvFoodCategory.text = "Sarapan" // Example category, you can add this to FoodItem
        binding.tvFoodDetailPrice.text = foodItem.price
        binding.tvFoodDetailRating.text = foodItem.rating.toString()
        binding.tvFoodDetailDescription.text = foodItem.description

        val favoriteIcon = if (foodItem.isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border
        // You may want a separate favorite icon for the toolbar
        // binding.ivFavoriteDetail.setImageResource(favoriteIcon)

        // Populate nutrition details
        binding.tvCaloriesValue.text = foodItem.calories
        binding.tvCarbsValue.text = foodItem.carbs
        binding.tvProteinValue.text = foodItem.protein
        binding.tvFatValue.text = foodItem.fat

        // Favorite logic would now likely be a menu item in the toolbar
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
