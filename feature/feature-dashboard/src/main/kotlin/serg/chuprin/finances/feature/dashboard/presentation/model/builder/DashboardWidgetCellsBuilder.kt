package serg.chuprin.finances.feature.dashboard.presentation.model.builder

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidgets
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
        widgets: DashboardWidgets,
        existingCells: List<BaseCell>
    ): List<DashboardWidgetCell> {
        return widgets.mapNotNull { (_, widget) ->
            // Do not rebuild cell if widget not changed.
            findExistingWidgetCell(widget, existingCells)
                ?: buildWidgetCell(widget)
        }
    }

    private fun findExistingWidgetCell(
        widget: DashboardWidget,
        existingCells: List<BaseCell>
    ): DashboardWidgetCell? {
        val existingWidgetCell = existingCells.find { cell ->
            cell is DashboardWidgetCell && cell.widget.type == widget.type
        } as? DashboardWidgetCell

        if (existingWidgetCell == null || existingWidgetCell.widget != widget) {
            return null
        }
        return existingWidgetCell
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