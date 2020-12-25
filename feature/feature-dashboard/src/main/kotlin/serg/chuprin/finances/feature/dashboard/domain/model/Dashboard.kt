package serg.chuprin.finances.feature.dashboard.domain.model

import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.period.DataPeriodType

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
data class Dashboard(
    val user: User = User.EMPTY,
    val hasNoMoneyAccounts: Boolean = true,
    val widgets: DashboardWidgets = DashboardWidgets(),
    val currentDataPeriod: DataPeriod = DataPeriod.from(DataPeriodType.DEFAULT)
)