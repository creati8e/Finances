package serg.chuprin.finances.feature.dashboard.presentation.model.builder

import serg.chuprin.finances.core.api.presentation.model.formatter.AmountFormatter
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardMoneyAccountCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 21.04.2020.
 */
class DashboardMoneyAccountsWidgetCellBuilder @Inject constructor(
    private val amountFormatter: AmountFormatter
) : DashboardWidgetCellBuilder {

    override fun build(widget: DashboardWidget): DashboardWidgetCell? {
        if (widget !is DashboardWidget.MoneyAccounts) {
            return null
        }
        val cells = widget
            .accountBalancesMap
            .map { (moneyAccount, balance) ->
                DashboardMoneyAccountCell(
                    name = moneyAccount.name,
                    moneyAccount = moneyAccount,
                    balance = amountFormatter.format(balance, moneyAccount.currency)
                )
            }
        return DashboardWidgetCell.MoneyAccounts(isExpanded = false, cells = cells, widget = widget)
    }

    override fun merge(
        newCell: DashboardWidgetCell,
        existingCell: DashboardWidgetCell
    ): DashboardWidgetCell? {
        if (newCell !is DashboardWidgetCell.MoneyAccounts) {
            return null
        }
        if (existingCell !is DashboardWidgetCell.MoneyAccounts) {
            return null
        }
        return newCell.copy(isExpanded = existingCell.isExpanded)
    }

}