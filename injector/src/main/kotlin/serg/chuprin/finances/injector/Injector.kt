package serg.chuprin.finances.injector

import serg.chuprin.finances.core.impl.di.CoreDependenciesComponent
import serg.chuprin.finances.feature.authorization.dependencies.AuthorizationDependencies
import serg.chuprin.finances.feature.authorization.dependencies.DaggerAuthorizationDependenciesComponent
import serg.chuprin.finances.feature.dashboard.dependencies.DaggerDashboardDependenciesComponent
import serg.chuprin.finances.feature.dashboard.dependencies.DashboardDependencies

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
object Injector {

    fun getAuthorizationDependencies(): AuthorizationDependencies {
        val coreProvider = CoreDependenciesComponent.get()
        return DaggerAuthorizationDependenciesComponent
            .builder()
            .coreGatewaysProvider(coreProvider)
            .coreNavigationProvider(coreProvider)
            .coreRepositoriesProvider(coreProvider)
            .build()
    }

    fun getDashboardDependencies(): DashboardDependencies {
        val coreProvider = CoreDependenciesComponent.get()
        return DaggerDashboardDependenciesComponent
            .builder()
            .coreRepositoriesProvider(coreProvider)
            .build()
    }

}