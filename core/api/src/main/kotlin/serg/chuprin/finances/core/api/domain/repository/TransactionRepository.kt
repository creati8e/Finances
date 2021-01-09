package serg.chuprin.finances.core.api.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.model.transaction.query.TransactionsQuery

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
interface TransactionRepository {

    fun createOrUpdate(transactions: Collection<Transaction>)

    fun transactionsFlow(query: TransactionsQuery): Flow<List<Transaction>>

    fun deleteTransactions(transactionIds: Collection<Id>)

    suspend fun transactions(query: TransactionsQuery): List<Transaction> {
        return transactionsFlow(query).firstOrNull().orEmpty()
    }

}