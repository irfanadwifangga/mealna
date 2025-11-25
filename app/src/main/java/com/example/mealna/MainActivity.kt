package com.example.mealna

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = FirebaseApp.initializeApp(this)
        if (app != null) {
            Log.d("FIREBASE", "Firebase BERHASIL TERHUBUNG!")
        } else {
            Log.e("FIREBASE", "Firebase GAGAL TERHUBUNG!")
        }
    }
}
