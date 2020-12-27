package serg.chuprin.finances.feature.dashboard.setup.impl.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import serg.chuprin.finances.core.api.data.datasource.preferences.Preference
import serg.chuprin.finances.core.api.data.datasource.preferences.PreferencesDataSource
import serg.chuprin.finances.feature.dashboard.setup.domain.model.CustomizableDashboardWidget
import serg.chuprin.finances.feature.dashboard.setup.domain.repository.DashboardWidgetsRepository
import serg.chuprin.finances.feature.dashboard.setup.impl.data.mapper.DashboardWidgetsPreferenceMapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 28.11.2020.
 */
internal class DashboardWidgetsRepositoryImpl @Inject constructor(
    preferencesDataSource: PreferencesDataSource,
    preferenceMapper: DashboardWidgetsPreferenceMapper
) : DashboardWidgetsRepository {

    override val widgetsFlow: Flow<Set<CustomizableDashboardWidget>>
        get() = preference.asFlow

    private val preference: Preference<Set<CustomizableDashboardWidget>> =
        preferencesDataSource.getCustomModel("dashboard_widgets", preferenceMapper)

    override fun updateWidgets(order: Set<CustomizableDashboardWidget>) {
        preference.value = order
    }

    override suspend fun getWidgets(): Set<CustomizableDashboardWidget> {
        return preference.asFlow.first()
    }

}