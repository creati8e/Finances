package serg.chuprin.finances.core.api.domain.repository

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
interface TransactionRepository {

    fun createTransaction(transaction: Transaction)

    fun moneyAccountTransactionsFlow(moneyAccountId: Id): Flow<List<Transaction>>

    /**
     * @return last [count] transactions in [dataPeriod].
     */
    fun userTransactionsFlow(
        userId: Id,
        count: Int = Int.MAX_VALUE,
        dataPeriod: DataPeriod? = null,
        includedCategoryIds: Set<Id> = emptySet(),
        transactionType: PlainTransactionType? = null
    ): Flow<List<Transaction>>

}