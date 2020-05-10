package serg.chuprin.finances.feature.dashboard.presentation.model.store

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.presentation.model.cells.DataPeriodTypePopupMenuCell

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
sealed class DashboardEvent {

    class ShowPeriodTypesPopupMenu(
        val menuCells: List<DataPeriodTypePopupMenuCell>
    ) : DashboardEvent()

    class NavigateToMoneyAccountDetailsScreen(
        val moneyAccountId: Id,
        val transitionName: String
    ) : DashboardEvent()

    object NavigateToMoneyAccountsListScreen : DashboardEvent()

    object NavigateToMoneyAccountCreationScreen : DashboardEvent()

}