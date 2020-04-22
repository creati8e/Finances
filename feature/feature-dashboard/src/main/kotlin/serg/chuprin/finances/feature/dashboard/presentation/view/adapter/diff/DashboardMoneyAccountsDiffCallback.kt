package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.diff

import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardMoneyAccountCell
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.diff.payload.DashboardMoneyAccountCellChangedPayload

/**
 * Created by Sergey Chuprin on 22.04.2020.
 */
class DashboardMoneyAccountsDiffCallback : DiffCallback<DashboardMoneyAccountCell>() {

    override fun getChangePayload(
        oldItem: DashboardMoneyAccountCell,
        newItem: DashboardMoneyAccountCell
    ): Any? {
        return DashboardMoneyAccountCellChangedPayload
    }

}