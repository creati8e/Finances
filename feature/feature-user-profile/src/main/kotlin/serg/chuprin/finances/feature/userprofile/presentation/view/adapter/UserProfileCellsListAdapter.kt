package serg.chuprin.finances.feature.userprofile.presentation.view.adapter

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.DividerAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.feature.userprofile.presentation.model.cells.UserProfileMenuCell
import serg.chuprin.finances.feature.userprofile.presentation.view.adapter.renderer.UserProfileDashboardWidgetsSetupCellRenderer
import serg.chuprin.finances.feature.userprofile.presentation.view.adapter.renderer.UserProfileDataPeriodTypeCellRenderer
import serg.chuprin.finances.feature.userprofile.presentation.view.adapter.renderer.UserProfileImageCellRenderer
import serg.chuprin.finances.feature.userprofile.presentation.view.adapter.renderer.UserProfileLogoutCellRenderer

/**
 * Created by Sergey Chuprin on 29.11.2020.
 */
class UserProfileCellsListAdapter : DiffMultiViewAdapter<BaseCell>(DiffCallback()), DividerAdapter {

    init {
        registerRenderer(UserProfileImageCellRenderer())
        registerRenderer(UserProfileLogoutCellRenderer())
        registerRenderer(UserProfileDataPeriodTypeCellRenderer())
        registerRenderer(UserProfileDashboardWidgetsSetupCellRenderer())
    }

    override fun shouldDrawDividerForCellAt(adapterPosition: Int): Boolean {
        val cell = items.getOrNull(adapterPosition) ?: return false
        if (cell !is UserProfileMenuCell) {
            return false
        }
        return items.getOrNull(adapterPosition + 1) is UserProfileMenuCell
    }
}