package serg.chuprin.finances.feature.dashboard.domain.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.linker.TransactionWithCategoriesLinker
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.query.TransactionCategoriesQuery
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.model.transaction.TransactionsQuery
import serg.chuprin.finances.core.api.domain.repository.TransactionCategoryRepository
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.core.api.extensions.categoryIds
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 26.12.2020.
 */
class DashboardCategoriesDataService @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: TransactionCategoryRepository,
    private val transactionWithCategoriesLinker: TransactionWithCategoriesLinker
) {

    fun dataFlow(
        currentUser: User,
        currentPeriod: DataPeriod,
        transactionType: PlainTransactionType
    ): Flow<Map<TransactionCategory?, List<Transaction>>> {
        // Get user transactions in period.
        return transactionRepository
            .transactionsFlow(
                TransactionsQuery(
                    userId = currentUser.id,
                    endDate = currentPeriod.endDate,
                    transactionType = transactionType,
                    startDate = currentPeriod.startDate
                )
            )
            // Get categories (with parents) for these transactions
            // and associate all categories with their parents.
            .flatMapLatest { transactions ->
                combine(
                    flowOf(transactions),
                    categoryRepository.categoriesFlow(
                        TransactionCategoriesQuery(
                            userId = currentUser.id,
                            categoryIds = transactions.categoryIds.toSet(),
                            relation = TransactionCategoriesQuery.Relation.RETRIEVE_PARENTS
                        )
                    ),
                    transactionWithCategoriesLinker::linkCategoryParentsWithTransactions
                )
            }
    }


}