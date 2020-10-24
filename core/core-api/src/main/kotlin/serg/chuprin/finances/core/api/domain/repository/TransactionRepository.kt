package serg.chuprin.finances.core.api.domain.repository

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.model.transaction.TransactionsQuery

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
interface TransactionRepository {

    fun createTransaction(transaction: Transaction)

    fun transactionsFlow(query: TransactionsQuery): Flow<List<Transaction>>

}