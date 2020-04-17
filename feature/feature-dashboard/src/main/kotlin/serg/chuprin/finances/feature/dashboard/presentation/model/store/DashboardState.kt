package serg.chuprin.finances.feature.dashboard.presentation.model.store

import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
data class DashboardState(
    val user: User = User.EMPTY,
    val cells: List<BaseCell> = emptyList()
)