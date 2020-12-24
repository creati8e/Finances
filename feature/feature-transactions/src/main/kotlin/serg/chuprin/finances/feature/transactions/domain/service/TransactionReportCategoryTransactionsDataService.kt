package serg.chuprin.finances.feature.transactions.domain.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.linker.TransactionWithCategoriesLinker
import serg.chuprin.finances.core.api.domain.model.CategoriesQueryResult
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.extensions.flow.distinctUntilChangedBy
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportFilter
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 24.12.2020.
 */
class TransactionReportCategoryTransactionsDataService @Inject constructor(
    private val transactionWithCategoriesLinker: TransactionWithCategoriesLinker
) {

    private companion object {

        /**
         * We want to build new data only if
         * [TransactionReportFilter.categoryIds] or [TransactionReportFilter.transactionType] has changed.
         *
         * @see [dataFlow]
         */
        private val INTERESTED_KEYS: List<((TransactionReportFilter) -> Any?)> = listOf(
            TransactionReportFilter::categoryIds,
            TransactionReportFilter::transactionType
        )

    }

    fun dataFlow(
        filterFlow: Flow<TransactionReportFilter>,
        transactionsFlow: Flow<List<Transaction>>,
        categoriesFlow: Flow<CategoriesQueryResult>
    ): Flow<Map<TransactionCategory?, List<Transaction>>> {
        return filterFlow
            .distinctUntilChangedBy(INTERESTED_KEYS)
            .flatMapLatest { filter ->
                when (filter) {
                    is TransactionReportFilter.Categories -> {
                        buildData(transactionsFlow, categoriesFlow)
                    }
                    is TransactionReportFilter.SingleCategory -> flowOf(emptyMap())
                    is TransactionReportFilter.Plain -> {
                        if (filter.transactionType == null) {
                            flowOf(emptyMap())
                        } else {
                            buildData(transactionsFlow, categoriesFlow)
                        }
                    }
                }
            }
    }

    private fun buildData(
        transactionsFlow: Flow<List<Transaction>>,
        categoriesFlow: Flow<CategoriesQueryResult>
    ): Flow<Map<TransactionCategory?, List<Transaction>>> {
        return combine(
            transactionsFlow,
            categoriesFlow,
            transactionWithCategoriesLinker::linkCategoryParentsWithTransactions
        )
    }

}