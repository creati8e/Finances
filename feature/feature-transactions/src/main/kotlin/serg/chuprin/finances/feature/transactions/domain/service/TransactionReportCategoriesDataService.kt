package serg.chuprin.finances.feature.transactions.domain.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import serg.chuprin.finances.core.api.domain.model.CategoriesQueryResult
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryType
import serg.chuprin.finances.core.api.domain.model.query.TransactionCategoriesQuery
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.domain.repository.TransactionCategoryRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.extensions.flow.distinctUntilChangedBy
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportFilter
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 22.12.2020.
 */
class TransactionReportCategoriesDataService @Inject constructor(
    private val userRepository: UserRepository,
    private val categoryRepository: TransactionCategoryRepository
) {

    private companion object {

        /**
         * We want to observe categories with new data from [TransactionReportFilter] only if
         * [TransactionReportFilter.categoryIds] or [TransactionReportFilter.transactionType] has changed.
         *
         * @see [dataFlow]
         */
        private val INTERESTED_KEYS: List<((TransactionReportFilter) -> Any?)> = listOf(
            TransactionReportFilter::categoryIds,
            TransactionReportFilter::transactionType
        )

    }

    /**
     * @return flow of [CategoriesQueryResult] which contains parent categories
     * from [TransactionReportFilter.categoryIds] with their children.
     *
     * If there's no categories in [TransactionReportFilter.categoryIds]
     * all categories returned for particular [TransactionReportFilter.transactionType].
     */
    suspend fun dataFlow(
        filterFlow: Flow<TransactionReportFilter>
    ): Flow<CategoriesQueryResult> {
        return filterFlow
            .distinctUntilChangedBy(INTERESTED_KEYS)
            .flatMapLatest { filter ->
                categoryRepository.categoriesFlow(buildQuery(filter))
            }
    }

    private suspend fun buildQuery(
        filter: TransactionReportFilter
    ): TransactionCategoriesQuery {
        // FIXME: Unify maybe.
        val categoryType = when (filter.transactionType) {
            PlainTransactionType.INCOME -> TransactionCategoryType.INCOME
            PlainTransactionType.EXPENSE -> TransactionCategoryType.EXPENSE
            null -> null
        }
        return TransactionCategoriesQuery(
            type = categoryType,
            categoryIds = filter.categoryIds,
            userId = userRepository.getCurrentUser().id,
            relation = TransactionCategoriesQuery.Relation.RETRIEVE_CHILDREN
        )
    }

}