package serg.chuprin.finances.feature.dashboard.presentation.model.store

import serg.chuprin.finances.core.api.presentation.model.cells.DataPeriodTypePopupMenuCell
import serg.chuprin.finances.core.api.presentation.model.cells.TransactionCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories.DashboardCategoryShareCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.moneyaccounts.DashboardMoneyAccountCell

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
sealed class DashboardIntent {

    object ClickOnZeroData : DashboardIntent()

    object ClickOnCurrentPeriod : DashboardIntent()

    object ClickOnNextPeriodButton : DashboardIntent()

    object ClickOnPreviousPeriodButton : DashboardIntent()

    object ClickOnMoneyAccountsListButton : DashboardIntent()

    object ClickOnCreateMoneyAccountButton : DashboardIntent()

    object ClickOnRestoreDefaultPeriodButton : DashboardIntent()

    object ClickOnCurrentPeriodIncomesButton : DashboardIntent()

    object ClickOnShowMoreTransactionsButton : DashboardIntent()

    object ClickOnCurrentPeriodExpensesButton : DashboardIntent()

    object ClickOnTransactionCreationButton : DashboardIntent()

    class ClickOnRecentTransactionCell(
        val transactionCell: TransactionCell
    ) : DashboardIntent()

    class ClickOnPeriodTypeCell(
        val periodTypeCell: DataPeriodTypePopupMenuCell
    ) : DashboardIntent()

    class ToggleMoneyAccountsVisibility(
        val widgetCell: DashboardWidgetCell.MoneyAccounts
    ) : DashboardIntent()

    class ClickOnMoneyAccount(
        val cell: DashboardMoneyAccountCell
    ) : DashboardIntent()

    class ClickOnCategory(
        val cell: DashboardCategoryShareCell
    ) : DashboardIntent()

}