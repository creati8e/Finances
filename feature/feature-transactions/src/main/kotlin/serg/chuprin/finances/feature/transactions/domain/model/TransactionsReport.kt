package serg.chuprin.finances.feature.transactions.domain.model

/**
 * Created by Sergey Chuprin on 06.07.2020.
 */
data class TransactionsReport(
    val filter: TransactionReportFilter,
    val preparedData: TransactionReportPreparedData
)