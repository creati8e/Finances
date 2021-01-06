package serg.chuprin.finances.core.firebase

import android.app.Application
import com.github.ajalt.timberkt.Timber
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import serg.chuprin.finances.core.api.di.Initializer

/**
 * Created by Sergey Chuprin on 14.04.2020.
 */
class FirebaseInitializer : Initializer {

    override fun initialize(application: Application) {
        val settings = FirebaseFirestoreSettings
            .Builder()
            .setPersistenceEnabled(true)
            .build()
        Firebase.firestore.firestoreSettings = settings
        Timber.d { "Firebase is initialized" }
    }

}