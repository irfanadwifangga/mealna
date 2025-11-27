package com.example.mealna

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mealna.classes.UserProfile
import com.example.mealna.classes.User
import com.example.mealna.databinding.ActivityProfileSetupBinding
import com.google.firebase.firestore.FirebaseFirestore

class ProfileSetupActivity : AppCompatActivity() {
    // Gunakan View Binding
    private lateinit var binding: ActivityProfileSetupBinding
    private lateinit var db: FirebaseFirestore

    private var userId: String? = null
    private var userEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        // Ambil data dari RegisterActivity
        userId = intent.getStringExtra("USER_ID")
        userEmail = intent.getStringExtra("EMAIL")

        if (userId == null || userEmail == null) {
            Toast.makeText(this, "Error: com.example.mealna.classes.User data not found.", Toast.LENGTH_LONG).show()
            // Kembali ke login jika tidak ada data user
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
        // 1. Ambil semua data dari input
        val name = binding.etName.text.toString().trim()
        val age = binding.etAge.text.toString().toIntOrNull() ?: 0
        val height = binding.etHeight.text.toString().toIntOrNull() ?: 0
        val weight = binding.etWeight.text.toString().toIntOrNull() ?: 0

        val selectedGenderId = binding.rgGender.checkedRadioButtonId
        val gender = if (selectedGenderId != -1) {
            findViewById<RadioButton>(selectedGenderId).text.toString().lowercase()
        } else {
            ""
        }

        val diseases = getCheckedItems(binding.llDiseases.id, listOf(R.id.cb_hypertension, R.id.cb_diabetes))
        val allergies = getCheckedItems(binding.llAllergies.id, listOf(R.id.cb_peanut, R.id.cb_seafood, R.id.cb_dairy))
        val dietPreferences = getCheckedItems(binding.llDiet.id, listOf(R.id.cb_low_carb, R.id.cb_vegetarian, R.id.cb_vegan))

        val goal = binding.spinnerGoal.selectedItem.toString()

        // 2. Validasi input
        if (name.isEmpty() || age <= 0 || height <= 0 || weight <= 0 || gender.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields correctly.", Toast.LENGTH_SHORT).show()
            return
        }

        // 3. Buat objek com.example.mealna.classes.UserProfile dan com.example.mealna.classes.User
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

        // 4. Simpan ke Firestore
        binding.btnSaveProfile.isEnabled = false // Disable tombol untuk mencegah klik ganda
        db.collection("USER").document(userId!!)
            .set(user)
            .addOnSuccessListener {
                Toast.makeText(this, "Profile saved successfully!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, HomeActivity::class.java)

                // Hapus semua activity sebelumnya dari back stack
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error saving profile: ${e.message}", Toast.LENGTH_LONG).show()
                binding.btnSaveProfile.isEnabled = true // Aktifkan kembali tombol jika gagal
            }
    }

    // Helper function untuk mengambil teks dari CheckBox yang tercentang
    private fun getCheckedItems(containerId: Int, checkBoxIds: List<Int>): List<String> {
        val selectedItems = mutableListOf<String>()
        val container = findViewById<android.view.ViewGroup>(containerId)
        checkBoxIds.forEach { id ->
            val checkBox = container.findViewById<CheckBox>(id)
            if (checkBox.isChecked) {
                selectedItems.add(checkBox.text.toString().lowercase())
            }
        }
        return selectedItems
    }
}