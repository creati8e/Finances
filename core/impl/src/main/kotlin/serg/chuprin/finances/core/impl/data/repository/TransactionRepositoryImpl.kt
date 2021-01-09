package serg.chuprin.finances.core.impl.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.model.transaction.query.TransactionsQuery
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

    override fun createOrUpdate(transactions: Collection<Transaction>) {
        firebaseDataSource.createOrUpdate(transactions)
    }

    override fun deleteTransactions(transactionIds: Collection<Id>) {
        firebaseDataSource.deleteTransactions(transactionIds)
    }

    override fun transactionsFlow(query: TransactionsQuery): Flow<List<Transaction>> {
        return firebaseDataSource
            .transactionsFlow(query)
            .map { transactions ->
                transactions.mapNotNull { documentSnapshot ->
                    mapper.mapFromSnapshot(documentSnapshot)
                        ?.takeIf { transaction ->
                            return@takeIf when (query.transactionType) {
                                PlainTransactionType.INCOME -> transaction.isIncome
                                PlainTransactionType.EXPENSE -> transaction.isExpense
                                null -> true
                            }
                        }
                        ?.takeIf { transaction ->
                            if (query.moneyAccountIds.isEmpty()) {
                                true
                            } else {
                                transaction.moneyAccountId in query.moneyAccountIds
                            }
                        }
                        ?.takeIf { transaction ->
                            if (query.categoryIds.isEmpty()) {
                                true
                            } else {
                                transaction.categoryId in query.categoryIds
                            }
                        }
                }
            }
            .flowOn(Dispatchers.Default)
    }

}