package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.moneyaccounts.diff

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.moneyaccounts.DashboardMoneyAccountCell

/**
 * Created by Sergey Chuprin on 22.04.2020.
 */
class DashboardMoneyAccountsDiffCallback : DiffCallback<BaseCell>() {

    override fun getChangePayload(
        oldCell: BaseCell,
        newCell: BaseCell
    ): Any? {
        if (oldCell is DashboardMoneyAccountCell && newCell is DashboardMoneyAccountCell) {
            return DashboardMoneyAccountCellChangedPayload
        }
        return super.getChangePayload(oldCell, newCell)
    }

}