package com.example.mealna

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory

class AppActivity : Application() {
    override fun onCreate() {
        super.onCreate()

        val app = FirebaseApp.initializeApp(this)
        if (app != null) {
            Log.d("FIREBASE", "Firebase BERHASIL TERHUBUNG dari Application Class!")
        } else {
            Log.e("FIREBASE", "Firebase GAGAL TERHUBUNG!")
        }

        // Initialize App Check with Debug Provider
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(
            DebugAppCheckProviderFactory.getInstance()
        )

        // Log untuk melihat token debug
        Log.d("APP_CHECK", "App Check initialized with Debug Provider")
    }
}
    