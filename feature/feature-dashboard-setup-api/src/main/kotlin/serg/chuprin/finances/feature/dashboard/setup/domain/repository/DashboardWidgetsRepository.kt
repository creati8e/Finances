package serg.chuprin.finances.feature.dashboard.setup.domain.repository

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.feature.dashboard.setup.domain.model.CustomizableDashboardWidget

/**
 * Created by Sergey Chuprin on 28.11.2020.
 */
interface DashboardWidgetsRepository {

    val widgetsFlow: Flow<Set<CustomizableDashboardWidget>>

    fun updateWidgets(order: Set<CustomizableDashboardWidget>)

    suspend fun getWidgets(): Set<CustomizableDashboardWidget>

}