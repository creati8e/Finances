package serg.chuprin.finances.feature.dashboard.presentation.model.cells

import serg.chuprin.finances.core.api.presentation.model.cells.DiffCell
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
sealed class DashboardWidgetCell(
    open val widget: DashboardWidget
) : DiffCell<DashboardWidget.Type> {

    data class DashboardHeaderCell(
        val balance: String,
        val currentPeriod: String,
        val expensesAmount: String,
        val incomesAmount: String,
        override val widget: DashboardWidget.Header
    ) : DashboardWidgetCell(widget)

    override val diffCellId: DashboardWidget.Type
        get() = widget.type

}