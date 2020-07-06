package serg.chuprin.finances.core.api.domain.service

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.TransactionCategoriesMap
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import java.time.LocalDateTime

/**
 * Created by Sergey Chuprin on 20.04.2020.
 *
 * This class retrieves transactions and categories.
 * It combines them in different ways depending of required operation.
 */
interface TransactionCategoryRetrieverService {

    fun moneyAccountTransactionsFlow(moneyAccountId: Id): Flow<TransactionCategoriesMap>

    fun userTransactionsFlow(
        userId: Id,
        count: Int = Int.MAX_VALUE,
        startDate: LocalDateTime? = null,
        endDate: LocalDateTime? = null,
        includedCategoryIds: Set<Id> = emptySet(),
        transactionType: PlainTransactionType? = null
    ): Flow<TransactionCategoriesMap>

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