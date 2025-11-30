package com.example.mealna

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mealna.databinding.ActivityAccountSettingsBinding

class AccountSettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupAccordions()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupAccordions() {
        binding.headerLanguage.setOnClickListener {
            toggleAccordion(binding.contentLanguage, binding.arrowLanguage)
        }

        binding.headerHelp.setOnClickListener {
            toggleAccordion(binding.contentHelp, binding.arrowHelp)
        }
    }

    private fun toggleAccordion(content: View, arrow: View) {
        if (content.visibility == View.GONE) {
            content.visibility = View.VISIBLE
            arrow.rotation = 180f
        } else {
            content.visibility = View.GONE
            arrow.rotation = 0f
        }
    }
}