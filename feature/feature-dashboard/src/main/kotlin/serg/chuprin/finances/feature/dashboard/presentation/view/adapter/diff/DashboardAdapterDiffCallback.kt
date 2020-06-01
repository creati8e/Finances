package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.diff

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.categories.diff.DashboardCategoriesWidgetChangedPayload
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.balance.diff.DashboardBalanceWidgetChangedPayload
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.moneyaccounts.diff.DashboardMoneyAccountCellsChangedPayload
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.moneyaccounts.diff.DashboardMoneyAccountsExpansionChangedPayload

/**
 * Created by Sergey Chuprin on 18.04.2020.
 */
class DashboardAdapterDiffCallback : DiffCallback<BaseCell>() {

    override fun getChangePayload(oldItem: BaseCell, newItem: BaseCell): Any? {
        if (oldItem is DashboardWidgetCell.Balance
            && newItem is DashboardWidgetCell.Balance
        ) {
            return DashboardBalanceWidgetChangedPayload
        }
        if (oldItem is DashboardWidgetCell.Categories
            && newItem is DashboardWidgetCell.Categories
        ) {
            return DashboardCategoriesWidgetChangedPayload
        }
        if (oldItem is DashboardWidgetCell.MoneyAccounts
            && newItem is DashboardWidgetCell.MoneyAccounts
        ) {
            if (oldItem.isExpanded != newItem.isExpanded) {
                return DashboardMoneyAccountsExpansionChangedPayload
            }
            if (oldItem.cells != newItem.cells) {
                return DashboardMoneyAccountCellsChangedPayload
            }
        }
        return super.getChangePayload(oldItem, newItem)
    }

}