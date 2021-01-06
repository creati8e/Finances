package serg.chuprin.finances.feature.dashboard.presentation.model.store

import serg.chuprin.finances.feature.dashboard.domain.model.Dashboard

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
sealed class DashboardAction {

    class FormatDashboard(
        val dashboard: Dashboard
    ) : DashboardAction()

    class ExecuteIntent(
        val intent: DashboardIntent
    ) : DashboardAction()

}