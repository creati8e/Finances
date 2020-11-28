package serg.chuprin.finances.feature.dashboard.setup.presentation.domain.repository

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.feature.dashboard.setup.presentation.domain.model.DashboardWidgetType

/**
 * Created by Sergey Chuprin on 28.11.2020.
 */
interface DashboardWidgetsRepository {

    val widgetsFlow: Flow<Set<DashboardWidgetType>>

    fun updateWidgets(order: Set<DashboardWidgetType>)

}