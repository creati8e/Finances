package serg.chuprin.finances.core.api.domain.repository

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.Transaction

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
interface TransactionRepository {

    suspend fun createTransaction(transaction: Transaction)

    fun userTransactionsFlow(userId: Id): Flow<List<Transaction>>

}