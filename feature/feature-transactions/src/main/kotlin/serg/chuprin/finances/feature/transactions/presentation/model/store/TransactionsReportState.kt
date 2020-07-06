package serg.chuprin.finances.feature.transactions.presentation.model.store

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell

/**
 * Created by Sergey Chuprin on 12.05.2020.
 */
data class TransactionsReportState(
    val cells: List<BaseCell> = emptyList()
)