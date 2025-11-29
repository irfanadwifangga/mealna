package com.example.mealna

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mealna.databinding.ActivityFavoritesBinding

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var favoritesAdapter: FavoritesAdapter
    private val favoriteItems = mutableListOf<FavoriteItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupRecyclerView()
        setupSwipeToDelete()

        // TODO: Load favorite items from data source
        // For now, we'll simulate an empty list
        updateFavoritesList(emptyList())
    }

    private fun setupRecyclerView() {
        favoritesAdapter = FavoritesAdapter(favoriteItems)
        binding.rvFavorites.apply {
            adapter = favoritesAdapter
            layoutManager = LinearLayoutManager(this@FavoritesActivity)
        }
    }

    private fun updateFavoritesList(favorites: List<FavoriteItem>) {
        favoriteItems.clear()
        favoriteItems.addAll(favorites)
        favoritesAdapter.notifyDataSetChanged()
        toggleEmptyState(favoriteItems.isEmpty())
    }

    private fun toggleEmptyState(isEmpty: Boolean) {
        if (isEmpty) {
            binding.groupEmptyState.visibility = View.VISIBLE
            binding.rvFavorites.visibility = View.GONE
        } else {
            binding.groupEmptyState.visibility = View.GONE
            binding.rvFavorites.visibility = View.VISIBLE
        }
    }

    private fun setupSwipeToDelete() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false // We don't want to handle move actions
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                favoriteItems.removeAt(position)
                favoritesAdapter.notifyItemRemoved(position)
                // After removing an item, check if the list is now empty
                toggleEmptyState(favoriteItems.isEmpty())
                // TODO: Add logic to remove the item from your data source
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvFavorites)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
