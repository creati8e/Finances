package serg.chuprin.finances.app.di.feature.dependencies

import dagger.Component
import serg.chuprin.finances.app.di.navigation.AppNavigationProvider
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.feature.dashboard.di.DashboardDependencies
import serg.chuprin.finances.feature.dashboard.setup.DashboardWidgetsSetupApi

/**
 * Created by Sergey Chuprin on 16.01.2021.
 */
@Component(
    dependencies = [
        CoreDependenciesProvider::class,
        DashboardWidgetsSetupApi::class,
        AppNavigationProvider::class
    ]
)
internal interface DashboardDependenciesComponent : DashboardDependencies