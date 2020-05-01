package serg.chuprin.finances.core.api.domain.service

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
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

    /**
     * @return flow of map with parent categories associated with transactions.
     * I.e transactions of child categories with specific parent associated with this parent.
     */
    fun userCategoryTransactionsInPeriod(
        userId: Id,
        dataPeriod: DataPeriod,
        transactionType: PlainTransactionType
    ): Flow<Map<TransactionCategory?, List<Transaction>>>

}