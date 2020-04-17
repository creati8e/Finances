package serg.chuprin.finances.feature.dashboard.presentation.model.store

import serg.chuprin.finances.core.api.domain.model.User

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
data class DashboardState(
    val user: User = User.EMPTY
)