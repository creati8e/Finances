package serg.chuprin.finances.feature.dashboard.domain.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.linker.TransactionWithCategoriesLinker
import serg.chuprin.finances.core.api.domain.model.CategoryToTransactionsList
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.category.query.CategoriesQuery
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.domain.model.transaction.query.TransactionsQuery
import serg.chuprin.finances.core.api.domain.repository.CategoryRepository
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.core.api.extensions.categoryIds
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 26.12.2020.
 */
class DashboardCategoriesDataService @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository,
    private val transactionWithCategoriesLinker: TransactionWithCategoriesLinker
) {

    fun dataFlow(
        currentUser: User,
        currentPeriod: DataPeriod,
        transactionType: PlainTransactionType
    ): Flow<CategoryToTransactionsList> {
        // Get user transactions in period.
        return transactionRepository
            .transactionsFlow(
                TransactionsQuery(
                    ownerId = currentUser.id,
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
                        CategoriesQuery(
                            ownerId = currentUser.id,
                            categoryIds = transactions.categoryIds,
                            relation = CategoriesQuery.Relation.RETRIEVE_PARENTS
                        )
                    ),
                    transactionWithCategoriesLinker::linkCategoryParentsWithTransactions
                )
            }
    }


}