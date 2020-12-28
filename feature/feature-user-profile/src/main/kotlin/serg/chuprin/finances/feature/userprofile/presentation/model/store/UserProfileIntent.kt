package serg.chuprin.finances.feature.userprofile.presentation.model.store

import serg.chuprin.finances.core.api.presentation.model.cells.DataPeriodTypePopupMenuCell
import serg.chuprin.finances.feature.userprofile.presentation.model.cells.UserProfileDataPeriodTypeCell

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
sealed class UserProfileIntent {

    class ClickOnDashboardWidgetsSetup(
        val transitionName: String
    ) : UserProfileIntent()

    class ClickOnPeriod(
        val periodTypeCell: UserProfileDataPeriodTypeCell
    ) : UserProfileIntent()

    class ClickOnPeriodTypeCell(
        val periodTypePopupMenuCell: DataPeriodTypePopupMenuCell
    ) : UserProfileIntent()

    object ClickOnLogOutButton : UserProfileIntent()

    object ClickOnCategoriesCell : UserProfileIntent()

    object ClickOnOnLogoutConfirmationButton : UserProfileIntent()

}