package com.example.mealna

import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory

class ReleaseAppCheckInstaller : AppCheckInstaller {
    override fun installAppCheck(firebaseAppCheck: FirebaseAppCheck) {
        firebaseAppCheck.installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance()
        )
    }
}
