package serg.chuprin.finances.core.impl.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.core.impl.data.datasource.firebase.FirebaseTransactionDataSource
import serg.chuprin.finances.core.impl.data.mapper.transaction.FirebaseTransactionMapper
import java.time.LocalDateTime
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

    override fun userTransactionsFlow(
        userId: Id,
        count: Int,
        startDate: LocalDateTime?,
        endDate: LocalDateTime?,
        includedCategoryIds: Set<Id>,
        transactionType: PlainTransactionType?
    ): Flow<List<Transaction>> {
        return firebaseDataSource
            .userTransactionsFlow(userId, count, startDate, endDate)
            .map { transactions ->
                transactions.mapNotNull { snapshot ->
                    mapper.mapFromSnapshot(snapshot)
                        ?.takeIf { transaction ->
                            return@takeIf when (transactionType) {
                                PlainTransactionType.INCOME -> transaction.isIncome
                                PlainTransactionType.EXPENSE -> transaction.isExpense
                                null -> true
                            }
                        }
                        ?.takeIf { transaction ->
                            if (includedCategoryIds.isEmpty()) {
                                true
                            } else {
                                val categoryId = transaction.categoryId
                                if (categoryId != null) {
                                    categoryId in includedCategoryIds
                                } else {
                                    false
                                }
                            }
                        }
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