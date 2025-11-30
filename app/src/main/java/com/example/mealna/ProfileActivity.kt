package com.example.mealna

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mealna.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        loadUserProfile()
        setupClickListeners()
    }

    private fun loadUserProfile() {
        val user = auth.currentUser
        user?.let {
            binding.tvUsername.text = it.displayName
            binding.tvEmail.text = it.email
            // You can also load the profile picture here using a library like Glide or Picasso
        }
    }

    private fun setupClickListeners() {
        binding.btnEditProfile.setOnClickListener {
            startActivity(Intent(this, ProfileSetupActivity::class.java))
        }

        binding.btnFavoriteFoods.setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
        }

        binding.btnSettings.setOnClickListener {
            startActivity(Intent(this, AccountSettingsActivity::class.java))
        }

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}