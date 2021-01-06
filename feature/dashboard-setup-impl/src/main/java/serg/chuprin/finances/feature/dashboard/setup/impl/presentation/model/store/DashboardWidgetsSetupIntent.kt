package serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.store

import serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.cells.DraggableDashboardWidgetCell

/**
 * Created by Sergey Chuprin on 27.11.2019.
 */
sealed class DashboardWidgetsSetupIntent {

    class MoveWidget(
        val fromPosition: Int,
        val toPosition: Int
    ) : DashboardWidgetsSetupIntent()

    class ToggleWidget(
        val widgetCell: DraggableDashboardWidgetCell
    ) : DashboardWidgetsSetupIntent()

    /**
     * User wants to save changed order or new fields turned on/off.
     */
    object ClickOnSaveIcon : DashboardWidgetsSetupIntent()

    object ResortWidgets : DashboardWidgetsSetupIntent()

}