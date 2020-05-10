package serg.chuprin.finances.feature.dashboard.presentation.model.store

import serg.chuprin.finances.core.api.presentation.model.cells.DataPeriodTypePopupMenuCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.moneyaccounts.DashboardMoneyAccountCell

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
sealed class DashboardIntent {

    object ClickOnCurrentPeriod : DashboardIntent()

    object ClickOnNextPeriodButton : DashboardIntent()

    object ClickOnPreviousPeriodButton : DashboardIntent()

    object ClickOnMoneyAccountsListButton : DashboardIntent()

    object ClickOnCreateMoneyAccountButton : DashboardIntent()

    object ClickOnRestoreDefaultPeriodButton : DashboardIntent()

    class ClickOnPeriodTypeCell(
        val periodTypeCell: DataPeriodTypePopupMenuCell
    ) : DashboardIntent()

    class ToggleMoneyAccountsVisibility(
        val widgetCell: DashboardWidgetCell.MoneyAccounts
    ) : DashboardIntent()

    class ClickOnMoneyAccount(
        val cell: DashboardMoneyAccountCell
    ) : DashboardIntent()

}