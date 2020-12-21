package serg.chuprin.finances.feature.transactions.presentation.model.store

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportFilter
import serg.chuprin.finances.feature.transactions.presentation.model.TransactionReportHeader
import serg.chuprin.finances.feature.transactions.presentation.model.cells.TransactionReportChartCell

/**
 * Created by Sergey Chuprin on 12.05.2020.
 */
sealed class TransactionsReportEffect {

    class ReportBuilt(
        val listCells: List<BaseCell>,
        val chartCells: List<TransactionReportChartCell>,
        val header: TransactionReportHeader,
        val filter: TransactionReportFilter
    ) : TransactionsReportEffect()

}