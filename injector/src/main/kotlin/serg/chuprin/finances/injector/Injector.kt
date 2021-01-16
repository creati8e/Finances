package serg.chuprin.finances.injector

import serg.chuprin.finances.core.impl.di.CoreDependenciesComponent
import serg.chuprin.finances.feature.main.dependencies.AuthorizedGraphLauncherDependencies
import serg.chuprin.finances.feature.main.dependencies.DaggerAuthorizedGraphLauncherDependenciesComponent

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
object Injector {

    fun getAuthorizedGraphLauncherDependencies(): AuthorizedGraphLauncherDependencies {
        return DaggerAuthorizedGraphLauncherDependenciesComponent
            .builder()
            .coreDependenciesProvider(CoreDependenciesComponent.get())
            .build()
    }

}