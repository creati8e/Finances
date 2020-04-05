package serg.chuprin.finances.app

import android.app.Application
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
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        initializeComponents()
    }

    private fun initializeComponents() {
        CoreDependenciesComponent.init(this, NavigationComponent.instance)
    }

}