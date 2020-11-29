package serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.store

import serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.cells.DraggableDashboardWidgetCell

/**
 * Created by Sergey Chuprin on 27.11.2019.
 */
sealed class DashboardWidgetsSetupEffect {

    class DefaultCellsBuilt(
        val cells: List<DraggableDashboardWidgetCell>
    ) : DashboardWidgetsSetupEffect()

    class CellsBuilt(
        val cells: List<DraggableDashboardWidgetCell>,
        val scrollToMovedWidget: Boolean
    ) : DashboardWidgetsSetupEffect()

}