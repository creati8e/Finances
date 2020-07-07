package serg.chuprin.finances.feature.transactions.presentation.arguments

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType

/**
 * Created by Sergey Chuprin on 06.07.2020.
 */
class TransactionsReportInitialFilter(
    val categoryIds: Set<Id?>,
    val dataPeriod: DataPeriod?,
    val plainTransactionType: PlainTransactionType?
)