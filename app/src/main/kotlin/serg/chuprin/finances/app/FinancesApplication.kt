package serg.chuprin.finances.app

import android.app.Application
import serg.chuprin.finances.app.di.feature.FeatureDependenciesProvider
import serg.chuprin.finances.app.di.feature.FeatureDependenciesProviderImpl
import serg.chuprin.finances.app.di.navigation.NavigationComponent
import serg.chuprin.finances.core.impl.di.CoreDependenciesComponent

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
@Suppress("unused")
class FinancesApplication : Application(),
    FeatureDependenciesProvider by FeatureDependenciesProviderImpl() {

    override fun onCreate() {
        super.onCreate()
        System.setProperty(
            "kotlinx.coroutines.debug",
            if (BuildConfig.DEBUG) "on" else "off"
        )
        initializeComponents()
    }

    private fun initializeComponents() {
        CoreDependenciesComponent.init(this, NavigationComponent.instance)
        CoreDependenciesComponent.get().appInitializer.initialize(this)
    }

}