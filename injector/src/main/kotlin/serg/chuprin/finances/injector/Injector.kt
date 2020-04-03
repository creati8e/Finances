package serg.chuprin.finances.injector

import serg.chuprin.finances.core.impl.di.CoreDependenciesComponent
import serg.chuprin.finances.feature.authorization.dependencies.AuthorizationDependencies
import serg.chuprin.finances.feature.authorization.dependencies.DaggerAuthorizationDependenciesComponent

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
object Injector {

    fun getAuthorizationDependencies(): AuthorizationDependencies {
        return DaggerAuthorizationDependenciesComponent
            .builder()
            .coreNavigationProvider(CoreDependenciesComponent.get())
            .build()
    }

}