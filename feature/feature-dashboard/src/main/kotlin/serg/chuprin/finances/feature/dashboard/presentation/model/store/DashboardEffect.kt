package serg.chuprin.finances.feature.dashboard.presentation.model.store

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.feature.dashboard.domain.model.Dashboard
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
sealed class DashboardEffect {

    class DashboardUpdated(
        val dashboard: Dashboard,
        val widgetCells: List<DashboardWidgetCell>
    ) : DashboardEffect()

    class CellsUpdated(
        val widgetCells: List<BaseCell>
    ) : DashboardEffect()

}