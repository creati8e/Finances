package serg.chuprin.finances.core.api.domain.service

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
interface TransactionCategoryRetrieverService {

    fun recentUserTransactionsInPeriodFlow(
        userId: Id,
        count: Int,
        dataPeriod: DataPeriod
    ): Flow<Map<Transaction, TransactionCategoryWithParent?>>

    fun userCategoryTransactionsInPeriod(
        userId: Id,
        dataPeriod: DataPeriod,
        transactionType: PlainTransactionType
    ): Flow<Map<TransactionCategoryWithParent?, List<Transaction>>>

}