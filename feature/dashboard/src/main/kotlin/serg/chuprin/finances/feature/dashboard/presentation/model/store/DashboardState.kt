package serg.chuprin.finances.feature.dashboard.presentation.model.store

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.feature.dashboard.domain.model.Dashboard
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardLoadingCell

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
data class DashboardState(
    val dashboard: Dashboard = Dashboard(),
    val cells: List<BaseCell> = listOf(DashboardLoadingCell())
)