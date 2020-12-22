package serg.chuprin.finances.feature.transactions.presentation.model.store

import serg.chuprin.finances.feature.transactions.presentation.model.cells.TransactionReportChartCell

/**
 * Created by Sergey Chuprin on 12.05.2020.
 */
sealed class TransactionsReportIntent {

    class ClickOnDataChartCell(
        val chartCell: TransactionReportChartCell
    ) : TransactionsReportIntent()

}