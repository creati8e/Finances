package serg.chuprin.finances.feature.userprofile.presentation.model.store

import serg.chuprin.finances.core.api.presentation.model.cells.DataPeriodTypePopupMenuCell

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
sealed class UserProfileEvent {

    object NavigateToLoginScreen : UserProfileEvent()

    object ShowLogoutConfirmDialog : UserProfileEvent()

    class NavigateToDashboardWidgetsSetupScreen(
        val transitionName: String
    ) : UserProfileEvent()

    class ShowPeriodTypesPopupMenu(
        val menuCells: List<DataPeriodTypePopupMenuCell>
    ) : UserProfileEvent()

}