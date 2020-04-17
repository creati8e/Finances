package serg.chuprin.finances.feature.dashboard.domain.model

import serg.chuprin.finances.core.api.domain.model.User

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
data class Dashboard(
    val user: User = User.EMPTY,
    val widgetsMap: Map<DashboardWidget.Type, DashboardWidget> = emptyMap()
)