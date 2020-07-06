package serg.chuprin.finances.core.api.domain.repository

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import java.time.LocalDateTime

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
interface TransactionRepository {

    fun createTransaction(transaction: Transaction)

    fun moneyAccountTransactionsFlow(moneyAccountId: Id): Flow<List<Transaction>>

    /**
     * @return last [count] transactions in [dataPeriod].
     */
    fun recentUserTransactionsFlow(
        userId: Id,
        count: Int,
        dataPeriod: DataPeriod
    ): Flow<List<Transaction>>

    fun userTransactionsFlow(
        userId: Id,
        startDate: LocalDateTime? = null,
        endDate: LocalDateTime? = null,
        includedCategoryIds: Set<Id> = emptySet(),
        transactionType: PlainTransactionType? = null
    ): Flow<List<Transaction>>

}