package serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.cells

import serg.chuprin.finances.core.api.presentation.model.cells.DiffCell
import serg.chuprin.finances.feature.dashboard.setup.domain.model.DashboardWidgetType

/**
 * Created by Sergey Chuprin on 29.11.2020.
 */
data class DraggableDashboardWidgetCell(
    val name: String,
    val isChecked: Boolean,
    val type: DashboardWidgetType
) : DiffCell<DashboardWidgetType> {

    override val diffCellId: DashboardWidgetType = type

}