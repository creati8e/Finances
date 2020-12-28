package serg.chuprin.finances.feature.userprofile.presentation.view.adapter

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.DividerAdapter
import serg.chuprin.finances.feature.userprofile.presentation.model.cells.UserProfileMenuCell
import serg.chuprin.finances.feature.userprofile.presentation.view.adapter.diff.UserProfileDiffCallback
import serg.chuprin.finances.feature.userprofile.presentation.view.adapter.renderer.*

/**
 * Created by Sergey Chuprin on 29.11.2020.
 */
class UserProfileCellsListAdapter : DiffMultiViewAdapter<BaseCell>(
    UserProfileDiffCallback()
), DividerAdapter {

    init {
        registerRenderer(UserProfileImageCellRenderer())
        registerRenderer(UserProfileLogoutCellRenderer())
        registerRenderer(UserProfileCategoriesCellRenderer())
        registerRenderer(UserProfileDataPeriodTypeCellRenderer())
        registerRenderer(UserProfileDashboardWidgetsSetupCellRenderer())
    }

    override fun shouldDrawDividerForCellAt(adapterPosition: Int): Boolean {
        return items.getOrNull(adapterPosition + 1) is UserProfileMenuCell
    }
}