package serg.chuprin.finances.feature.dashboard.presentation.model.cells

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
data class DashboardHeaderCell(
    val balance: String,
    val currentPeriod: String,
    val expensesAmount: String,
    val incomesAmount: String
) : BaseCell