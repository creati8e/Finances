package serg.chuprin.finances.app

import android.app.Application
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import serg.chuprin.finances.app.di.navigation.NavigationComponent
import serg.chuprin.finances.core.impl.di.CoreDependenciesComponent
import timber.log.Timber

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
@Suppress("unused")
class FinancesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        System.setProperty(
            "kotlinx.coroutines.debug",
            if (BuildConfig.DEBUG) "on" else "off"
        )
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        initializeComponents()
        // TODO: Move setup to other place.
        val settings = FirebaseFirestoreSettings
            .Builder()
            .setPersistenceEnabled(true)
            .build()
        Firebase.firestore.firestoreSettings = settings
    }

    private fun initializeComponents() {
        CoreDependenciesComponent.init(this, NavigationComponent.instance)
    }

}