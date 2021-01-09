package serg.chuprin.finances.feature.transactions.domain.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.CategoryIdToCategory
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.model.transaction.query.TransactionsQuery
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.extensions.flow.distinctUntilChangedBy
import serg.chuprin.finances.feature.transactions.domain.model.ReportDataPeriod
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportFilter
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 22.12.2020.
 */
class TransactionReportTransactionDataService @Inject constructor(
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository
) {

    private companion object {

        /**
         * We want to observe transactions with new data from [TransactionReportFilter] only if
         * [TransactionReportFilter.reportDataPeriod] or [TransactionReportFilter.transactionType] has changed.
         *
         * @see [dataFlow]
         */
        private val INTERESTED_KEYS: List<((TransactionReportFilter) -> Any?)> = listOf(
            TransactionReportFilter::reportDataPeriod,
            TransactionReportFilter::transactionType
        )

    }

    suspend fun dataFlow(
        filterFlow: Flow<TransactionReportFilter>,
        categoryIdToCategoryFlow: SharedFlow<CategoryIdToCategory>
    ): Flow<List<Transaction>> {
        return combine(
            categoryIdToCategoryFlow,
            filterFlow.distinctUntilChangedBy(INTERESTED_KEYS),
            ::Pair
        ).flatMapLatest { (categories, filter) ->
            transactionRepository.transactionsFlow(buildQuery(filter, categories))
        }
    }

    private suspend fun buildQuery(
        filter: TransactionReportFilter,
        categoryIdToCategory: CategoryIdToCategory
    ): TransactionsQuery {
        // If report has non-custom and non-all-time period type,
        // get data for all periods from min to max.
        // This data is required for data period amounts chart building.
        val (startDate, endDate) = when (val reportDataPeriod = filter.reportDataPeriod) {
            ReportDataPeriod.AllTime, is ReportDataPeriod.Predefined -> null to null
            is ReportDataPeriod.Custom -> {
                reportDataPeriod.startDate to reportDataPeriod.endDate
            }
        }

        val initialCategoryIds: MutableSet<Id?> = if (
            Id.UNKNOWN in filter.categoryIds
            || filter.categoryIds.isEmpty()
        ) {
            mutableSetOf(null)
        } else {
            mutableSetOf()
        }

        return TransactionsQuery(
            endDate = endDate,
            startDate = startDate,
            transactionType = filter.transactionType,
            ownerId = userRepository.getCurrentUser().id,
            sortOrder = TransactionsQuery.SortOrder.DATE_ASC,
            categoryIds = categoryIdToCategory.mapTo(initialCategoryIds, { (categoryId) ->
                categoryId
            })
        )
    }

}