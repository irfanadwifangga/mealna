package com.example.mealna

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val app = FirebaseApp.initializeApp(this)
        if (app != null) {
            Log.d("FIREBASE", "Firebase BERHASIL TERHUBUNG!")
        } else {
            Log.e("FIREBASE", "Firebase GAGAL TERHUBUNG!")
        }
    }
}
