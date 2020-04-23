package serg.chuprin.finances.feature.dashboard.presentation.model.store

import serg.chuprin.finances.core.api.presentation.model.cells.DataPeriodTypePopupMenuCell

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
sealed class DashboardEvent {

    class ShowPeriodTypesPopupMenu(
        val menuCells: List<DataPeriodTypePopupMenuCell>
    ) : DashboardEvent()

}