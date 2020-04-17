package serg.chuprin.finances.feature.dashboard.presentation.model.builder

import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
class DashboardHeaderWidgetCellBuilder @Inject constructor() : DashboardWidgetCellBuilder {

    override fun build(
        widget: DashboardWidget
    ): DashboardWidgetCell? {
        if (widget !is DashboardWidget.Header) {
            return null
        }
        // TODO: Add real formatting.
        return DashboardWidgetCell.DashboardHeaderCell(
            balance = widget.balance.toString(),
            currentPeriod = widget.dataPeriod.periodType.toString(),
            expensesAmount = widget.currentPeriodExpenses.toString(),
            incomesAmount = widget.currentPeriodIncomes.toString()
        )
    }

}