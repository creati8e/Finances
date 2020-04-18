package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.diff

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.diff.payload.DashboardHeaderWidgetChangedPayload

/**
 * Created by Sergey Chuprin on 18.04.2020.
 */
class DashboardAdapterDiffCallback : DiffCallback<BaseCell>() {

    override fun getChangePayload(oldItem: BaseCell, newItem: BaseCell): Any? {
        if (oldItem is DashboardWidgetCell.DashboardHeaderCell
            && newItem is DashboardWidgetCell.DashboardHeaderCell
        ) {
            return DashboardHeaderWidgetChangedPayload
        }
        return super.getChangePayload(oldItem, newItem)
    }

}