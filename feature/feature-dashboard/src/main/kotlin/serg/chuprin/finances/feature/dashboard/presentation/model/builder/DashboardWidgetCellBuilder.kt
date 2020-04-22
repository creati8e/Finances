package serg.chuprin.finances.feature.dashboard.presentation.model.builder

import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
interface DashboardWidgetCellBuilder {

    /**
     * @return new cell for [widget] or null if builder does not support this type of widget.
     */
    fun build(widget: DashboardWidget): DashboardWidgetCell?

    /**
     * @return new cell retrieved by merging [newCell] with [existingCell].
     * Not all types of widgets support merging.
     * If particular widget does not has state for merging, just return null
     */
    fun merge(
        newCell: DashboardWidgetCell,
        existingCell: DashboardWidgetCell
    ): DashboardWidgetCell? {
        return null
    }

}