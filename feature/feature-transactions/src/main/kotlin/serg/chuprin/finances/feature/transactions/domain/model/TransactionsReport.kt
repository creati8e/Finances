package serg.chuprin.finances.feature.transactions.domain.model

import serg.chuprin.finances.core.api.domain.model.User

/**
 * Created by Sergey Chuprin on 06.07.2020.
 */
data class TransactionsReport(
    val currentUser: User,
    val filter: TransactionReportFilter,
    val preparedData: TransactionReportPreparedData
)