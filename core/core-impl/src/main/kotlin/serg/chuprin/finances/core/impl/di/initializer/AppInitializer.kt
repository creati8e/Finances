package serg.chuprin.finances.core.impl.di.initializer

import android.app.Application
import serg.chuprin.finances.core.api.di.Initializer
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 14.04.2020.
 */
internal class AppInitializer @Inject constructor(
    private val initializers: Set<@JvmSuppressWildcards Initializer>
) : Initializer {

    override fun initialize(application: Application) {
        initializers.forEach { initializer -> initializer.initialize(application) }
    }

}