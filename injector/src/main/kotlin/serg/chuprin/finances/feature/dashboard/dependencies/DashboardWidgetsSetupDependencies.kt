package serg.chuprin.finances.feature.dashboard.dependencies

import dagger.Component
import serg.chuprin.finances.core.api.data.datasource.preferences.PreferencesDataSource
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider

/**
 * Created by Sergey Chuprin on 28.11.2020.
 */
interface DashboardWidgetsSetupDependencies {
    val preferencesDataSource: PreferencesDataSource
}

@Component(dependencies = [CoreDependenciesProvider::class])
internal interface DashboardWidgetsSetupDependenciesComponent : DashboardWidgetsSetupDependencies