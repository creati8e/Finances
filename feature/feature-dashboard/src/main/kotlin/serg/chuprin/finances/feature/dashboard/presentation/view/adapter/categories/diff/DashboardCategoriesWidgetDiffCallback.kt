package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.categories.diff

import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories.page.DashboardCategoriesPageCell
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.categories.diff.payload.DashboardCategoriesPageChangedPayload

/**
 * Created by Sergey Chuprin on 28.04.2020.
 */
class DashboardCategoriesWidgetDiffCallback : DiffCallback<DashboardCategoriesPageCell>() {

    override fun getChangePayload(
        oldItem: DashboardCategoriesPageCell,
        newItem: DashboardCategoriesPageCell
    ): Any? {
        return DashboardCategoriesPageChangedPayload
    }

}