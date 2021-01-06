package serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.store

import serg.chuprin.finances.feature.dashboard.setup.domain.model.CustomizableDashboardWidget

/**
 * Created by Sergey Chuprin on 27.11.2019.
 */
sealed class DashboardWidgetsSetupAction {

    class BuildCells(
        val widgets: Set<CustomizableDashboardWidget>
    ) : DashboardWidgetsSetupAction()

    class Execute(
        val intent: DashboardWidgetsSetupIntent
    ) : DashboardWidgetsSetupAction()

}