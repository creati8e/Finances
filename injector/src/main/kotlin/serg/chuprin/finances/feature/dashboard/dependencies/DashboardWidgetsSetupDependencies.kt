package serg.chuprin.finances.feature.dashboard.dependencies

import dagger.Component
import serg.chuprin.finances.core.api.data.datasource.preferences.PreferencesDataSource
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger

/**
 * Created by Sergey Chuprin on 28.11.2020.
 */
interface DashboardWidgetsSetupDependencies {
    val resourceManger: ResourceManger
    val preferencesDataSource: PreferencesDataSource
}

@Component(dependencies = [CoreDependenciesProvider::class])
internal interface DashboardWidgetsSetupDependenciesComponent : DashboardWidgetsSetupDependencies