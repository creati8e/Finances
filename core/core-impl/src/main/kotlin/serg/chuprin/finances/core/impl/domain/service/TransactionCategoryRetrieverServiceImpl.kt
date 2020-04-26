package serg.chuprin.finances.core.impl.domain.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.repository.TransactionCategoryRepository
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.core.api.domain.service.TransactionCategoryRetrieverService
import serg.chuprin.finances.core.api.extensions.categoryIds
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
internal class TransactionCategoryRetrieverServiceImpl @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: TransactionCategoryRepository
) : TransactionCategoryRetrieverService {

    override fun recentUserTransactionsInPeriodFlow(
        userId: Id,
        count: Int,
        dataPeriod: DataPeriod
    ): Flow<Map<Transaction, TransactionCategoryWithParent?>> {
        return transactionRepository
            .recentUserTransactionsFlow(userId, count, dataPeriod)
            .flatMapLatest { transactions ->
                combine(
                    flowOf(transactions),
                    categoryRepository.categoriesFlow(transactions.categoryIds)
                ) { t1, t2 ->
                    suspendCoroutine<Map<Transaction, TransactionCategoryWithParent?>> {
                        it.resume(associateTransactionsWithCategories(t1, t2))
                    }
                }
            }
    }

    override fun userCategoryTransactionsInPeriod(
        userId: Id,
        dataPeriod: DataPeriod,
        transactionType: PlainTransactionType
    ): Flow<Map<TransactionCategoryWithParent?, List<Transaction>>> {
        return transactionRepository
            .userTransactionsFlow(userId, dataPeriod, transactionType)
            .flatMapLatest { transactions ->
                combine(
                    flowOf(transactions),
                    categoryRepository.categoriesFlow(transactions.categoryIds)
                ) { t1, t2 ->
                    suspendCoroutine<Map<TransactionCategoryWithParent?, List<Transaction>>> {
                        it.resume(associateCategoriesWithTransactions(t1, t2))
                    }
                }
            }
    }

    private fun associateCategoriesWithTransactions(
        transactions: List<Transaction>,
        categoryWithParentMap: Map<Id, TransactionCategoryWithParent>
    ): Map<TransactionCategoryWithParent?, List<Transaction>> {
        return transactions.groupBy { transaction ->
            categoryWithParentMap[transaction.categoryId]
        }
    }

    private fun associateTransactionsWithCategories(
        transactions: List<Transaction>,
        categoryWithParentMap: Map<Id, TransactionCategoryWithParent>
    ): Map<Transaction, TransactionCategoryWithParent?> {
        return transactions.associateBy(
            { transaction -> transaction },
            { transaction -> categoryWithParentMap[transaction.categoryId] }
        )
    }

}