package serg.chuprin.finances.feature.dashboard.presentation.model.cells

import serg.chuprin.finances.core.api.presentation.model.cells.DiffCell
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
sealed class DashboardWidgetCell(
    private val widgetType: DashboardWidget.Type
) : DiffCell<DashboardWidget.Type> {

    data class DashboardHeaderCell(
        val balance: String,
        val currentPeriod: String,
        val expensesAmount: String,
        val incomesAmount: String
    ) : DashboardWidgetCell(DashboardWidget.Type.HEADER)

    override val diffCellId: DashboardWidget.Type
        get() = widgetType

}