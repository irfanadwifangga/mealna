package com.example.mealna

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mealna.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
class RegisterActivity : AppCompatActivity() {private lateinit var binding: ActivityRegisterBinding
private lateinit var auth: FirebaseAuth

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityRegisterBinding.inflate(layoutInflater)
    setContentView(binding.root)

    auth = FirebaseAuth.getInstance()

    binding.btnRegister.setOnClickListener {
        registerUser()
    }

    binding.tvLogin.setOnClickListener {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}

private fun registerUser() {
    val email = binding.etEmail.text.toString().trim()
    val password = binding.etPassword.text.toString().trim()
    val confirmPassword = binding.etConfirmPassword.text.toString().trim()

    // 1. VALIDASI INPUT
    if (!validateInput(email, password, confirmPassword)) {
        return // Hentikan fungsi jika validasi gagal
    }

    // 2. MANAJEMEN UI STATE
    showLoading(true)

    // 3. PROSES REGISTER DENGAN FIREBASE
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(this) { task ->
            showLoading(false) // Sembunyikan loading setelah selesai

            if (task.isSuccessful) {
                Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
                navigateToProfileSetup(auth.currentUser?.uid, email)
            } else {
                // 4. PENANGANAN ERROR SPESIFIK
                val exception = task.exception
                val errorMessage = when (exception) {
                    is FirebaseAuthUserCollisionException -> "Email ini sudah terdaftar. Silakan login."
                    else -> "Registrasi gagal: ${exception?.message}"
                }
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            }
        }
}

private fun validateInput(email: String, password: String, confirmPass: String): Boolean {
    if (email.isEmpty()) {
        binding.tilEmail.error = "Email harus diisi"
        binding.etEmail.requestFocus()
        return false
    }
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        binding.tilEmail.error = "Format email tidak valid"
        binding.etEmail.requestFocus()
        return false
    }
    binding.tilEmail.error = null

    if (password.isEmpty()) {
        binding.tilPassword.error = "Password harus diisi"
        binding.etPassword.requestFocus()
        return false
    }
    if (password.length < 6) {
        binding.tilPassword.error = "Password minimal 6 karakter"
        binding.etPassword.requestFocus()
        return false
    }
    binding.tilPassword.error = null

    if (confirmPass != password) {
        binding.tilConfirmPassword.error = "Password tidak sama"
        binding.etConfirmPassword.requestFocus()
        return false
    }
    binding.tilConfirmPassword.error = null

    return true // Semua validasi berhasil
}

private fun showLoading(isLoading: Boolean) {
    if (isLoading) {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnRegister.isEnabled = false
        binding.btnRegister.text = ""
    } else {
        binding.progressBar.visibility = View.GONE
        binding.btnRegister.isEnabled = true
        binding.btnRegister.text = "Register"
    }
}

private fun navigateToProfileSetup(uid: String?, email: String) {
    val intent = Intent(this, ProfileSetupActivity::class.java).apply {
        putExtra("USER_ID", uid)
        putExtra("EMAIL", email)
    }
    startActivity(intent)
    finish() // Tutup activity ini agar tidak bisa kembali ke halaman register
}}