package serg.chuprin.finances.core.impl.domain.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.model.*
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

    override fun getLastTransactions(
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
                        it.resume(assignCategories(t1, t2))
                    }
                }
            }
    }

    private fun assignCategories(
        transactions: List<Transaction>,
        categories: List<TransactionCategory>
    ): Map<Transaction, TransactionCategoryWithParent?> {
        val categoryWithParentMap = categories.associateBy(
            { category -> category.id },
            { category ->
                TransactionCategoryWithParent(
                    category = category,
                    parentCategory = categories.find { it.parentCategoryId == category.parentCategoryId }
                )
            }
        )
        return transactions.associateBy(
            { transaction -> transaction },
            { transaction -> categoryWithParentMap[transaction.categoryId] }
        )
    }

}