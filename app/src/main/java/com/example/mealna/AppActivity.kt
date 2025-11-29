package com.example.mealna

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import java.util.ServiceLoader

class AppActivity : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Initialize Firebase App Check
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        val appCheckInstaller = ServiceLoader.load(AppCheckInstaller::class.java).firstOrNull()
        appCheckInstaller?.installAppCheck(firebaseAppCheck)
    }
}
