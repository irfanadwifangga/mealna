package com.example.mealna

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mealna.classes.User
import com.example.mealna.classes.UserProfile
import com.example.mealna.databinding.ActivityProfileSetupBinding
import com.google.firebase.firestore.FirebaseFirestore

class ProfileSetupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileSetupBinding
    private lateinit var db: FirebaseFirestore

    private var userId: String? = null
    private var userEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        userId = intent.getStringExtra("USER_ID")
        userEmail = intent.getStringExtra("EMAIL")

        if (userId == null || userEmail == null) {
            Toast.makeText(this, "Error: User data not found.", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setupSpinner()

        binding.btnSaveProfile.setOnClickListener {
            saveProfileData()
        }
    }

    private fun setupSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.goal_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerGoal.adapter = adapter
        }
    }

    private fun saveProfileData() {
        val name = binding.etName.text.toString().trim()
        val age = binding.etAge.text.toString().toIntOrNull()
        val height = binding.etHeight.text.toString().toIntOrNull()
        val weight = binding.etWeight.text.toString().toDoubleOrNull()

        val selectedGenderId = binding.rgGender.checkedRadioButtonId
        val gender = if (selectedGenderId != -1) {
            findViewById<RadioButton>(selectedGenderId).text.toString().lowercase()
        } else {
            ""
        }

        if (name.isEmpty() || age == null || age <= 0 || height == null || height <= 0 || weight == null || weight <= 0 || gender.isEmpty()) {
            Toast.makeText(this, "Harap isi semua kolom dengan benar.", Toast.LENGTH_SHORT).show()
            return
        }

        val diseases = mutableListOf<String>().apply {
            if (binding.cbHypertension.isChecked) add("hypertension")
            if (binding.cbDiabetes.isChecked) add("diabetes")
        }

        val allergies = mutableListOf<String>().apply {
            if (binding.cbPeanut.isChecked) add("peanut")
            if (binding.cbSeafood.isChecked) add("seafood")
            if (binding.cbDairy.isChecked) add("dairy")
        }

        val dietPreferences = mutableListOf<String>().apply {
            if (binding.cbLowCarb.isChecked) add("low-carb")
            if (binding.cbVegetarian.isChecked) add("vegetarian")
            if (binding.cbVegan.isChecked) add("vegan")
        }

        val goal = binding.spinnerGoal.selectedItem.toString()

        val userProfile = UserProfile(
            height = height,
            weight = weight,
            age = age,
            gender = gender,
            diseases = diseases,
            allergies = allergies,
            dietPreferences = dietPreferences,
            goal = goal
        )

        val user = User(
            uid = userId!!,
            name = name,
            email = userEmail!!,
            profile = userProfile
        )

        binding.btnSaveProfile.isEnabled = false
        db.collection("users").document(userId!!)
            .set(user)
            .addOnSuccessListener {
                Toast.makeText(this, "Profil berhasil disimpan!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, OnboardingActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error saat menyimpan profil: ${e.message}", Toast.LENGTH_LONG).show()
                binding.btnSaveProfile.isEnabled = true
            }
    }
}
