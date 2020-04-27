package serg.chuprin.finances.core.impl.di.initializer

import android.app.Application
import serg.chuprin.finances.core.api.di.Initializer
import serg.chuprin.finances.core.impl.BuildConfig
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 14.04.2020.
 */
internal class TimberInitializer @Inject constructor() : Initializer {

    override fun initialize(application: Application) {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Timber.d("Timber is initialized")
    }

}