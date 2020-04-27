package serg.chuprin.finances.core.impl.presentation.debug

import android.app.Application
import com.github.ajalt.timberkt.Timber
import com.pandulapeter.beagle.Beagle
import com.pandulapeter.beagleCore.configuration.Trick
import serg.chuprin.finances.core.api.di.Initializer
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.impl.BuildConfig
import serg.chuprin.finances.core.api.R as CoreR

/**
 * Created by Sergey Chuprin on 27.04.2020.
 */
internal class AppDebugMenuImpl(
    private val resourceManger: ResourceManger
) : Initializer {

    override fun initialize(application: Application) {
        Timber.d { "Debug menu is initialized" }
        with(Beagle) {
            imprint(application)
            learn(
                Trick.Header(
                    title = resourceManger.getString(CoreR.string.app_name),
                    subtitle = "v${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
                )
            )
        }
    }

}