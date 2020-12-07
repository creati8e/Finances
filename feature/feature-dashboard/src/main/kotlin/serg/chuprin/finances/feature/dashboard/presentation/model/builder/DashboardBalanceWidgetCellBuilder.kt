package serg.chuprin.finances.feature.dashboard.presentation.model.builder

import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
class DashboardBalanceWidgetCellBuilder @Inject constructor(
    private val amountFormatter: AmountFormatter
) : DashboardWidgetCellBuilder {

    override fun build(
        widget: DashboardWidget
    ): DashboardWidgetCell? {
        if (widget !is DashboardWidget.Balance) {
            return null
        }
        return DashboardWidgetCell.Balance(
            widget = widget,
            balance = formatAmount(widget.balance, widget.currency),
            incomesAmount = formatAmount(widget.currentPeriodIncomes, widget.currency),
            expensesAmount = formatAmount(widget.currentPeriodExpenses, widget.currency)
        )
    }

    private fun formatAmount(amount: BigDecimal, currency: Currency): String {
        return amountFormatter.format(
            round = true,
            amount = amount,
            withSign = false,
            currency = currency,
            withCurrencySymbol = true
        )
    }
}