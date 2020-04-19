package serg.chuprin.finances.feature.dashboard.presentation.model.builder

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.feature.dashboard.domain.model.Dashboard
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 19.04.2020.
 *
 * This class builds cells for [DashboardWidget].
 * It tries to reuse existing cell for particular widget if it is not changed.
 */
class DashboardWidgetCellsBuilder @Inject constructor(
    private val widgetCellBuilders: @JvmSuppressWildcards Set<DashboardWidgetCellBuilder>
) {

    fun build(
        dashboard: Dashboard,
        existingDashboard: Dashboard,
        existingCells: List<BaseCell>
    ): List<DashboardWidgetCell> {
        return dashboard.widgetsMap.mapNotNull { (_, widget) ->
            // Do not rebuild cell if widget not changed.
            findExistingWidgetCell(widget, existingDashboard, existingCells)
                ?: buildWidgetCell(widget)
        }
    }

    private fun findExistingWidgetCell(
        widget: DashboardWidget,
        previousDashboard: Dashboard,
        previousCells: List<BaseCell>
    ): DashboardWidgetCell? {
        val existingWidget = previousDashboard.widgetsMap[widget.type]
        if (existingWidget == null || existingWidget != widget) {
            return null
        }
        return previousCells.find { cell ->
            cell is DashboardWidgetCell && cell.widgetType == widget.type
        } as? DashboardWidgetCell
    }

    private fun buildWidgetCell(widget: DashboardWidget): DashboardWidgetCell? {
        var cell: DashboardWidgetCell?
        widgetCellBuilders.forEach { builder ->
            cell = builder.build(widget)
            if (cell != null) {
                return cell
            }
        }
        return null
    }

}