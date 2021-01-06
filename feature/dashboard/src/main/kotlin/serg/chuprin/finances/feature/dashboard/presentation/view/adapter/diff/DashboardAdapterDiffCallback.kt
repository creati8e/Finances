package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.diff

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.balance.diff.DashboardBalanceWidgetChangedPayload
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.categories.diff.DashboardCategoriesWidgetChangedPayload
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.moneyaccounts.diff.DashboardMoneyAccountCellsChangedPayload
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.moneyaccounts.diff.DashboardMoneyAccountsExpansionChangedPayload
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.period.diff.DashboardPeriodSelectorWidgetCellChangedPayload

/**
 * Created by Sergey Chuprin on 18.04.2020.
 */
class DashboardAdapterDiffCallback : DiffCallback<BaseCell>() {

    override fun getChangePayload(oldCell: BaseCell, newCell: BaseCell): Any? {
        if (oldCell is DashboardWidgetCell.Balance
            && newCell is DashboardWidgetCell.Balance
        ) {
            return DashboardBalanceWidgetChangedPayload
        }
        if (oldCell is DashboardWidgetCell.PeriodSelector
            && newCell is DashboardWidgetCell.PeriodSelector
        ) {
            return DashboardPeriodSelectorWidgetCellChangedPayload
        }
        if (oldCell is DashboardWidgetCell.Categories
            && newCell is DashboardWidgetCell.Categories
        ) {
            return DashboardCategoriesWidgetChangedPayload
        }
        if (oldCell is DashboardWidgetCell.MoneyAccounts
            && newCell is DashboardWidgetCell.MoneyAccounts
        ) {
            if (oldCell.isExpanded != newCell.isExpanded) {
                return DashboardMoneyAccountsExpansionChangedPayload
            }
            if (oldCell.cells != newCell.cells) {
                return DashboardMoneyAccountCellsChangedPayload
            }
        }
        return super.getChangePayload(oldCell, newCell)
    }

}