package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.moneyaccounts.diff

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.moneyaccounts.DashboardMoneyAccountCell
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.moneyaccounts.diff.payload.DashboardMoneyAccountCellChangedPayload

/**
 * Created by Sergey Chuprin on 22.04.2020.
 */
class DashboardMoneyAccountsDiffCallback : DiffCallback<BaseCell>() {

    override fun getChangePayload(
        oldItem: BaseCell,
        newItem: BaseCell
    ): Any? {
        if (oldItem is DashboardMoneyAccountCell && newItem is DashboardMoneyAccountCell) {
            return DashboardMoneyAccountCellChangedPayload
        }
        return super.getChangePayload(oldItem, newItem)
    }

}