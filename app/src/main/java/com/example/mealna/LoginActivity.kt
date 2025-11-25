package com.example.mealna

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var loginButton: Button
    private lateinit var signUpTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Initialize views
        emailEditText = findViewById(R.id.et_email)
        passwordEditText = findViewById(R.id.et_password)
        emailInputLayout = findViewById(R.id.til_email)
        passwordInputLayout = findViewById(R.id.til_password)
        loginButton = findViewById(R.id.btn_login)
        signUpTextView = findViewById(R.id.tv_signup)

        // Set click listener for login button
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            var isValid = true

            if (email.isEmpty()) {
                emailInputLayout.error = "Email is required"
                isValid = false
            } else {
                emailInputLayout.error = null
            }

            if (password.isEmpty()) {
                passwordInputLayout.error = "Password is required"
                isValid = false
            } else {
                passwordInputLayout.error = null
            }

            if (isValid) {
                // TODO: Add Firebase authentication logic here
                Toast.makeText(this, "Login button clicked", Toast.LENGTH_SHORT).show()
            }
        }

        // Set click listener for sign up text
        signUpTextView.setOnClickListener {
            // TODO: Create and navigate to RegisterActivity
             Toast.makeText(this, "Navigate to Sign Up screen", Toast.LENGTH_SHORT).show()
            // val intent = Intent(this, RegisterActivity::class.java)
            // startActivity(intent)
        }
    }
}
