package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.diff

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.diff.payload.DashboardHeaderWidgetChangedPayload
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.diff.payload.DashboardMoneyAccountsExpansionChangedPayload

/**
 * Created by Sergey Chuprin on 18.04.2020.
 */
class DashboardAdapterDiffCallback : DiffCallback<BaseCell>() {

    override fun getChangePayload(oldItem: BaseCell, newItem: BaseCell): Any? {
        if (oldItem is DashboardWidgetCell.Header
            && newItem is DashboardWidgetCell.Header
        ) {
            return DashboardHeaderWidgetChangedPayload
        }
        if (oldItem is DashboardWidgetCell.MoneyAccounts
            && newItem is DashboardWidgetCell.MoneyAccounts
        ) {
            if (oldItem.isExpanded != newItem.isExpanded) {
                return DashboardMoneyAccountsExpansionChangedPayload
            }
        }
        return super.getChangePayload(oldItem, newItem)
    }

}