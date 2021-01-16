package serg.chuprin.finances.app.di.feature.dependencies

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.feature.dashboard.presentation.di.DashboardDependencies
import serg.chuprin.finances.feature.dashboard.setup.DashboardWidgetsSetupApi

/**
 * Created by Sergey Chuprin on 16.01.2021.
 */
@Component(
    dependencies = [
        CoreDependenciesProvider::class,
        DashboardWidgetsSetupApi::class
    ]
)
internal interface DashboardDependenciesComponent : DashboardDependencies