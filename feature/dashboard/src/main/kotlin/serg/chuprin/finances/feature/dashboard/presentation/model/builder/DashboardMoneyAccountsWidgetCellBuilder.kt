package serg.chuprin.finances.feature.dashboard.presentation.model.builder

import serg.chuprin.finances.core.api.presentation.builder.TransitionNameBuilder
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.moneyaccounts.DashboardMoneyAccountCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.moneyaccounts.DashboardMoneyAccountWidgetZeroDataCell
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 21.04.2020.
 */
class DashboardMoneyAccountsWidgetCellBuilder @Inject constructor(
    private val amountFormatter: AmountFormatter,
    private val transitionNameBuilder: TransitionNameBuilder
) : DashboardWidgetCellBuilder {

    override fun build(widget: DashboardWidget): DashboardWidgetCell? {
        if (widget !is DashboardWidget.MoneyAccounts) {
            return null
        }
        return DashboardWidgetCell.MoneyAccounts(
            widget = widget,
            isExpanded = false,
            cells = buildCells(widget)
        )
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

    private fun buildCells(widget: DashboardWidget.MoneyAccounts): List<BaseCell> {
        if (widget.moneyAccountToBalance.isEmpty()) {
            return listOf(DashboardMoneyAccountWidgetZeroDataCell())
        }
        return widget
            .moneyAccountToBalance
            .map { (moneyAccount, balance) ->
                DashboardMoneyAccountCell(
                    name = moneyAccount.name,
                    moneyAccount = moneyAccount,
                    favoriteIconIsVisible = moneyAccount.isFavorite,
                    balance = amountFormatter.format(balance, moneyAccount.currency),
                    transitionName = transitionNameBuilder
                        .buildForForMoneyAccountDetails(moneyAccount.id)
                )
            }
    }

}