package serg.chuprin.finances.feature.userprofile.presentation.view.adapter.diff

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.feature.userprofile.presentation.model.cells.UserProfileDataPeriodTypeCell
import serg.chuprin.finances.feature.userprofile.presentation.model.cells.UserProfileImageCell
import serg.chuprin.finances.feature.userprofile.presentation.model.cells.UserProfileLogoutCell
import serg.chuprin.finances.feature.userprofile.presentation.model.cells.UserProfileSetupDashboardWidgetsCell

/**
 * Created by Sergey Chuprin on 06.12.2020.
 */
class UserProfileDiffCallback : DiffCallback<BaseCell>() {

    override fun areItemsTheSame(oldCell: BaseCell, newCell: BaseCell): Boolean {
        if (oldCell is UserProfileDataPeriodTypeCell && newCell is UserProfileDataPeriodTypeCell) {
            return true
        }
        return super.areItemsTheSame(oldCell, newCell)
    }

    @Suppress("ReplaceCallWithBinaryOperator")
    override fun areContentsTheSame(oldCell: BaseCell, newCell: BaseCell): Boolean {
        if (oldCell is UserProfileLogoutCell && newCell is UserProfileLogoutCell) {
            return true
        }
        if (oldCell is UserProfileSetupDashboardWidgetsCell
            && newCell is UserProfileSetupDashboardWidgetsCell
        ) {
            return true
        }
        if (oldCell is UserProfileDataPeriodTypeCell && newCell is UserProfileDataPeriodTypeCell) {
            return oldCell.equals(newCell)
        }
        if (oldCell is UserProfileImageCell && newCell is UserProfileImageCell) {
            return oldCell.equals(newCell)
        }
        return super.areContentsTheSame(oldCell, newCell)
    }

    override fun getChangePayload(oldCell: BaseCell, newCell: BaseCell): Any? {
        if (oldCell is UserProfileDataPeriodTypeCell && newCell is UserProfileDataPeriodTypeCell) {
            return UserProfilePeriodChangedDiffPayload()
        }
        if (oldCell is UserProfileImageCell && newCell is UserProfileImageCell) {
            return UserProfileChangedDiffPayload()
        }
        return super.getChangePayload(oldCell, newCell)
    }

}