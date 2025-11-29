package com.example.mealna

import com.google.firebase.appcheck.FirebaseAppCheck

interface AppCheckInstaller {
    fun installAppCheck(firebaseAppCheck: FirebaseAppCheck)
}
