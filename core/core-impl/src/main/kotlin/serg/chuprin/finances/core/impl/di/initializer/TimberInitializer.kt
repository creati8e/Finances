package serg.chuprin.finances.core.impl.di.initializer

import serg.chuprin.finances.core.api.di.Initializer
import serg.chuprin.finances.core.impl.BuildConfig
import timber.log.Timber

/**
 * Created by Sergey Chuprin on 14.04.2020.
 */
class TimberInitializer : Initializer {

    override fun initialize() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Timber.d("Timber is initialized")
    }

}