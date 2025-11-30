package com.example.mealna

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * A temporary, minimal activity used to diagnose startup crashes.
 * It uses no custom theme and performs no special initialization.
 */
class DebugLauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = TextView(this).apply {
            text = "Debug Activity Started Successfully!"
            textSize = 24f
        }
        setContentView(textView)
    }
}
