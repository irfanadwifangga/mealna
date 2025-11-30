package com.example.mealna

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.mealna.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the splash screen transition.
        installSplashScreen()

        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        sharedPreferences = getSharedPreferences("MealNaPrefs", Context.MODE_PRIVATE)

        // The theme-based splash screen will automatically dismiss. We just need to check the user session.
        checkUserSession()

        binding.btnLogin.setOnClickListener {
            performLogin()
        }

        binding.tvSignup.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun checkUserSession() {
        if (auth.currentUser != null) {
            // User is logged in, navigate to the correct screen.
            // The splash screen will dismiss as this activity finishes.
            checkOnboardingStatus()
        } else {
            // No user is logged in. The login screen is already visible.
            // No special action is needed to dismiss the splash screen.
        }
    }

    private fun performLogin() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (email.isEmpty()) {
            binding.tilEmail.error = "Email harus diisi"
            binding.etEmail.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = "Format email tidak valid"
            binding.etEmail.requestFocus()
            return
        }

        binding.tilEmail.error = null

        if (password.isEmpty()) {
            binding.tilPassword.error = "Password harus diisi"
            binding.etPassword.requestFocus()
            return
        }

        binding.tilPassword.error = null

        showLoading(true)

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                showLoading(false)

                if (task.isSuccessful) {
                    Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show()
                    checkOnboardingStatus()
                } else {
                    val exception = task.exception
                    val errorMessage = when (exception) {
                        is FirebaseAuthInvalidUserException -> "Email tidak terdaftar."
                        is FirebaseAuthInvalidCredentialsException -> "Password salah."
                        else -> "Login gagal: ${exception?.message}"
                    }
                    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun checkOnboardingStatus() {
        val hasSeenOnboarding = sharedPreferences.getBoolean("has_seen_onboarding", false)

        val intent = if (hasSeenOnboarding) {
            Intent(this, HomeActivity::class.java)
        } else {
            sharedPreferences.edit().putBoolean("has_seen_onboarding", true).apply()
            Intent(this, OnboardingActivity::class.java)
        }
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnLogin.isEnabled = false
            binding.btnLogin.text = ""
        } else {
            binding.progressBar.visibility = View.GONE
            binding.btnLogin.isEnabled = true
            binding.btnLogin.text = "Login"
        }
    }
}
