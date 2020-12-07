package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.categories.diff

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories.page.DashboardCategoriesPageCell

/**
 * Created by Sergey Chuprin on 28.04.2020.
 */
class DashboardCategoryPagesDiffCallback : DiffCallback<BaseCell>() {

    override fun getChangePayload(oldCell: BaseCell, newCell: BaseCell): Any? {
        if (oldCell is DashboardCategoriesPageCell && newCell is DashboardCategoriesPageCell) {
            return DashboardCategoriesPageChangedPayload
        }
        return super.getChangePayload(oldCell, newCell)
    }

}