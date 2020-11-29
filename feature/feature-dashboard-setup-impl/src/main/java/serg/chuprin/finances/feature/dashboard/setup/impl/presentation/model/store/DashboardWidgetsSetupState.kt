package serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.store

import serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.cells.DraggableDashboardWidgetCell


/**
 * Created by Sergey Chuprin on 27.11.2019.
 */
data class DashboardWidgetsSetupState(
    val scrollToMovedWidget: Boolean = false,
    val widgetCells: List<DraggableDashboardWidgetCell> = emptyList(),
    val defaultWidgetCells: List<DraggableDashboardWidgetCell> = emptyList()
) {

    val hasChanges: Boolean
        get() = widgetCells != defaultWidgetCells

}