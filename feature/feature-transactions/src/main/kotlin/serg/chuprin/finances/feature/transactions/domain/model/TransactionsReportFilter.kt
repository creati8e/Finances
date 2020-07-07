package serg.chuprin.finances.feature.transactions.domain.model

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.period.ReportDataPeriod
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType

/**
 * Created by Sergey Chuprin on 12.05.2020.
 */
data class TransactionsReportFilter(
    val includedCategoryIds: Set<Id?> = emptySet(),
    val transactionType: PlainTransactionType? = null,
    val dataPeriod: ReportDataPeriod = ReportDataPeriod.AllTime
)