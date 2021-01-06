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
            buildCell(widget, existingCells)
        }
    }

    private fun buildCell(
        widget: DashboardWidget,
        existingCells: List<BaseCell>
    ): DashboardWidgetCell {
        // Do not rebuild cell if widget not changed.
        val existingWidgetCell = existingCells
            .find { cell ->
                cell is DashboardWidgetCell && cell.widget.type == widget.type
            } as? DashboardWidgetCell
            ?: return buildWidgetCell(widget)

        // Widgets are not changed, return existing.
        if (existingWidgetCell.widget == widget) {
            return existingWidgetCell
        }
        // Widgets are changed. Build new widget and try to merge state with existing one.
        return mergeWidgetCells(buildWidgetCell(widget), existingWidgetCell)
    }

    private fun mergeWidgetCells(
        newCell: DashboardWidgetCell,
        existingCell: DashboardWidgetCell
    ): DashboardWidgetCell {
        var mergedCell: DashboardWidgetCell?
        widgetCellBuilders.forEach { builder ->
            mergedCell = builder.merge(
                newCell = newCell,
                existingCell = existingCell
            )
            if (mergedCell != null) {
                return mergedCell!!
            }
        }
        return newCell
    }

    private fun buildWidgetCell(widget: DashboardWidget): DashboardWidgetCell {
        var cell: DashboardWidgetCell?
        widgetCellBuilders.forEach { builder ->
            cell = builder.build(widget)
            if (cell != null) {
                return cell!!
            }
        }
        throw IllegalStateException("Widget cell builder not found for widget ${widget.type}")
    }

}