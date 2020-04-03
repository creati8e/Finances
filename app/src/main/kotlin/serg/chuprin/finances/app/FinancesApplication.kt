package serg.chuprin.finances.app

import android.app.Application
import serg.chuprin.finances.BuildConfig
import serg.chuprin.finances.core.impl.di.CoreComponent
import timber.log.Timber

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
class FinancesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        initializeComponents()
    }

    private fun initializeComponents() {
        CoreComponent.init(this)
    }

}