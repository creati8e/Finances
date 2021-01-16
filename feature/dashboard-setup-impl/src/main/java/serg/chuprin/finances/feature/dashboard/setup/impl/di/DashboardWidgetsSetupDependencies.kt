package serg.chuprin.finances.feature.dashboard.setup.impl.di

import serg.chuprin.finances.core.api.data.datasource.preferences.PreferencesDataSource
import serg.chuprin.finances.core.api.di.dependencies.FeatureDependencies
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger

/**
 * Created by Sergey Chuprin on 28.11.2020.
 */
interface DashboardWidgetsSetupDependencies : FeatureDependencies {
    val resourceManger: ResourceManger
    val preferencesDataSource: PreferencesDataSource
}