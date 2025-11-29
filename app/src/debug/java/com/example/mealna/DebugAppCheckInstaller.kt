package com.example.mealna

import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory

class DebugAppCheckInstaller : AppCheckInstaller {
    override fun installAppCheck(firebaseAppCheck: FirebaseAppCheck) {
        firebaseAppCheck.installAppCheckProviderFactory(
            DebugAppCheckProviderFactory.getInstance()
        )
    }
}
