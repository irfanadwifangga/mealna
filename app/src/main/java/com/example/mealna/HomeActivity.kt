package com.example.mealna

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealna.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var foodAdapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        setupRecyclerView()
        setupFilterChips()
        setupNavigation()

        // Load initial data for the default checked chip
        updateFoodList("Sarapan")
    }

    private fun setupNavigation() {
        binding.ivProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.ivNotifications.setOnClickListener {
            startActivity(Intent(this, AccountSettingsActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
        foodAdapter = FoodAdapter()
        binding.rvFoodList.apply {
            adapter = foodAdapter
            layoutManager = LinearLayoutManager(this@HomeActivity)
        }
    }

    private fun setupFilterChips() {
        binding.categoryChipGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedCategory = when (checkedId) {
                R.id.chip_breakfast -> "Sarapan"
                R.id.chip_lunch -> "Makan Siang"
                R.id.chip_dinner -> "Makan Malam"
                else -> null
            }
            selectedCategory?.let { updateFoodList(it) }
        }
    }

    private fun updateFoodList(category: String) {
        val newList = createDummyDataForCategory(category)
        foodAdapter.submitList(newList)
    }

    private fun createDummyDataForCategory(category: String): List<FoodItem> {
        return when (category) {
            "Sarapan" -> listOf(
                FoodItem(
                    imageUrl = "",
                    title = "Oatmeal dengan Susu & Buah",
                    description = "Meningkatkan energi & konsentrasi belajar",
                    rating = 4.8f,
                    price = "Rp20.000",
                    calories = "230-280", carbs = "40g", protein = "10g", fat = "6g"
                ),
                FoodItem(
                    imageUrl = "",
                    title = "Omelet (Telur, sayur, keju parut)",
                    description = "Protein tinggi membantu regenerasi sel & kenyang lebih lama",
                    rating = 4.7f,
                    price = "Rp15.000",
                    calories = "210-250", carbs = "4g", protein = "14g", fat = "20g"
                )
            )
            "Makan Siang" -> listOf(
                FoodItem(
                    imageUrl = "",
                    title = "Nasi Goreng Sehat",
                    description = "Nasi goreng dengan sedikit minyak dan banyak sayuran.",
                    rating = 4.6f,
                    price = "Rp25.000",
                    calories = "350-400", carbs = "50g", protein = "15g", fat = "12g"
                )
            )
            "Makan Malam" -> listOf(
                FoodItem(
                    imageUrl = "",
                    title = "Salad Ayam Panggang",
                    description = "Salad segar dengan potongan ayam panggang tanpa kulit.",
                    rating = 4.9f,
                    price = "Rp35.000",
                    calories = "280-320", carbs = "10g", protein = "25g", fat = "15g"
                )
            )
            else -> emptyList()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
