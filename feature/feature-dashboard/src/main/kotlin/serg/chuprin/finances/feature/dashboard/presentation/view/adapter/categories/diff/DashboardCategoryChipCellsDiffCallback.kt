package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.categories.diff

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories.DashboardCategoryChipCell

/**
 * Created by Sergey Chuprin on 28.04.2020.
 */
class DashboardCategoryChipCellsDiffCallback : DiffCallback<BaseCell>() {

    override fun getChangePayload(oldCell: BaseCell, newCell: BaseCell): Any? {
        if (oldCell is DashboardCategoryChipCell && newCell is DashboardCategoryChipCell) {
            return DashboardCategoryChipCellChangedPayload
        }
        return super.getChangePayload(oldCell, newCell)
    }

}