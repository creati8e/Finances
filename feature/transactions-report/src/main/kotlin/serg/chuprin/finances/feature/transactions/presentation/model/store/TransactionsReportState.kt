package serg.chuprin.finances.feature.transactions.presentation.model.store

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportFilter
import serg.chuprin.finances.feature.transactions.presentation.model.TransactionReportHeader

/**
 * Created by Sergey Chuprin on 12.05.2020.
 */
data class TransactionsReportState(
    val listCells: List<BaseCell> = emptyList(),
    val header: TransactionReportHeader = TransactionReportHeader(),
    val filter: TransactionReportFilter = TransactionReportFilter.UNINITIALIZED
)