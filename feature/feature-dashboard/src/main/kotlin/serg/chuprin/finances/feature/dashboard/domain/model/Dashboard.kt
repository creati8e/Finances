package serg.chuprin.finances.feature.dashboard.domain.model

import serg.chuprin.finances.core.api.domain.model.DataPeriod
import serg.chuprin.finances.core.api.domain.model.DataPeriodType
import serg.chuprin.finances.core.api.domain.model.User

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
data class Dashboard(
    val user: User = User.EMPTY,
    val widgets: DashboardWidgets = DashboardWidgets(),
    val currentDataPeriod: DataPeriod = DataPeriod.from(DataPeriodType.DEFAULT)
)