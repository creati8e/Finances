package serg.chuprin.finances.feature.transactions.domain.model

import serg.chuprin.finances.core.api.domain.model.TransactionsGroupedByDay
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction

/**
 * Created by Sergey Chuprin on 13.12.2020.
 */
data class TransactionReportPreparedData(
    val transactionsGroupedByDay: TransactionsGroupedByDay,
    val chartData: Map<DataPeriod, List<Transaction>>
)