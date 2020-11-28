package serg.chuprin.finances.feature.dashboard.setup.impl.data.repository

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.data.datasource.preferences.Preference
import serg.chuprin.finances.core.api.data.datasource.preferences.PreferencesDataSource
import serg.chuprin.finances.feature.dashboard.setup.impl.data.mapper.DashboardWidgetsPreferenceMapper
import serg.chuprin.finances.feature.dashboard.setup.presentation.domain.model.DashboardWidgetType
import serg.chuprin.finances.feature.dashboard.setup.presentation.domain.repository.DashboardWidgetsRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 28.11.2020.
 */
internal class DashboardWidgetsRepositoryImpl @Inject constructor(
    preferencesDataSource: PreferencesDataSource,
    preferenceMapper: DashboardWidgetsPreferenceMapper
) : DashboardWidgetsRepository {

    override val widgetsFlow: Flow<Set<DashboardWidgetType>>
        get() = preference.asFlow

    private val preference: Preference<Set<DashboardWidgetType>> =
        preferencesDataSource.getCustomModel("dashboard_widgets", preferenceMapper)

    override fun updateWidgets(order: Set<DashboardWidgetType>) {
        preference.value = order
    }

}