package serg.chuprin.finances.feature.dashboard.presentation.model.builder

import serg.chuprin.finances.core.api.presentation.model.formatter.AmountFormatter
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 24.04.2020.
 */
class DashboardCategoriesWidgetCellBuilder @Inject constructor(
    private val amountFormatter: AmountFormatter
) : DashboardWidgetCellBuilder {

    override fun build(widget: DashboardWidget): DashboardWidgetCell? {
        if (widget !is DashboardWidget.Categories) {
            return null
        }
        // TODO:
        return DashboardWidgetCell.Categories(
            totalAmount = amountFormatter.format(
                amount = widget.totalAmount,
                currency = widget.currency
            ),
            transactionsType = "Expense", widget = widget
        )
    }

}