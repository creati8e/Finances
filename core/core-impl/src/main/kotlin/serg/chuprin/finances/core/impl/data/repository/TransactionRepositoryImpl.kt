package serg.chuprin.finances.core.impl.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.core.impl.data.datasource.firebase.FirebaseTransactionDataSource
import serg.chuprin.finances.core.impl.data.mapper.transaction.FirebaseTransactionMapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
internal class TransactionRepositoryImpl @Inject constructor(
    private val mapper: FirebaseTransactionMapper,
    private val firebaseDataSource: FirebaseTransactionDataSource
) : TransactionRepository {

    override fun createTransaction(transaction: Transaction) {
        firebaseDataSource.createTransaction(transaction)
    }

    // TODO: Filter not balance.
    override fun userTransactionsFlow(
        userId: Id,
        dataPeriod: DataPeriod?,
        transactionType: PlainTransactionType?
    ): Flow<List<Transaction>> {
        return firebaseDataSource
            .userTransactionsFlow(userId, dataPeriod)
            .map { transactions ->
                if (transactionType != null) {
                    transactions.mapNotNull { snapshot ->
                        mapper.mapFromSnapshot(snapshot)?.takeIf { transaction ->
                            when (transactionType) {
                                PlainTransactionType.INCOME -> transaction.isIncome
                                PlainTransactionType.EXPENSE -> transaction.isExpense
                            }
                        }
                    }
                } else {
                    transactions.mapNotNull(mapper::mapFromSnapshot)
                }
            }
            .flowOn(Dispatchers.Default)
    }

    override fun recentUserTransactionsFlow(
        userId: Id,
        count: Int,
        dataPeriod: DataPeriod
    ): Flow<List<Transaction>> {
        return firebaseDataSource
            .recentUserTransactionsFlow(userId, count, dataPeriod)
            .map { transactions ->
                transactions.mapNotNull { snapshot ->
                    mapper
                        .mapFromSnapshot(snapshot)
                        ?.takeIf { transaction -> !transaction.isBalance }
                }
            }
            .flowOn(Dispatchers.Default)
    }

    override fun moneyAccountTransactionsFlow(moneyAccountId: Id): Flow<List<Transaction>> {
        return firebaseDataSource
            .moneyAccountTransactionsFlow(moneyAccountId)
            .map { transactions -> transactions.mapNotNull(mapper::mapFromSnapshot) }
            .flowOn(Dispatchers.Default)
    }

}