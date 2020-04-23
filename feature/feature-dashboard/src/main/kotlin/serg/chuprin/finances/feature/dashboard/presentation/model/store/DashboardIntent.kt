package serg.chuprin.finances.feature.dashboard.presentation.model.store

import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
sealed class DashboardIntent {

    object ClickOnCurrentPeriod : DashboardIntent()

    object ClickOnNextPeriodButton : DashboardIntent()

    object ClickOnPreviousPeriodButton : DashboardIntent()

    object ClickOnRestoreDefaultPeriodButton : DashboardIntent()

    class ToggleMoneyAccountsVisibility(
        val widgetCell: DashboardWidgetCell.MoneyAccounts
    ) : DashboardIntent()

}