package serg.chuprin.finances.feature.transactions.domain.builder

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import serg.chuprin.finances.core.api.domain.linker.TransactionWithCategoriesLinker
import serg.chuprin.finances.core.api.domain.model.CategoriesQueryResult
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.period.DataPeriodType
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.extensions.flow.distinctUntilChangedBy
import serg.chuprin.finances.feature.transactions.domain.model.ReportDataPeriod
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportFilter
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 21.12.2020.
 *
 * This builder creates data for current period in [TransactionReportFilter].
 */
class TransactionReportListDataBuilder @Inject constructor(
    private val transactionWithCategoriesLinker: TransactionWithCategoriesLinker
) {

    private companion object {

        /**
         * We want to rebuild list data if report period type has changed
         * i.e from [ReportDataPeriod.Predefined] to [ReportDataPeriod.Custom]
         * or from [DataPeriodType.DAY] to [DataPeriodType.MONTH].
         *
         * @see [buildData]
         */
        private val INTERESTED_KEYS: List<((TransactionReportFilter) -> Any?)> = listOf(
            TransactionReportFilter::reportDataPeriod
        )

    }

    fun dataFlow(
        transactionsFlow: Flow<List<Transaction>>,
        categoriesFlow: Flow<CategoriesQueryResult>,
        filterFlow: Flow<TransactionReportFilter>
    ): Flow<Map<Transaction, TransactionCategoryWithParent?>> {
        return combine(
            transactionsFlow,
            categoriesFlow,
            filterFlow.distinctUntilChangedBy(INTERESTED_KEYS),
            ::buildData
        )
    }

    private fun buildData(
        transactions: List<Transaction>,
        categoriesQueryResult: CategoriesQueryResult,
        filter: TransactionReportFilter
    ): Map<Transaction, TransactionCategoryWithParent?> {
        val transactionsInCurrentPeriod = transactions.filter { transaction ->
            transaction.dateTime in filter.reportDataPeriod
        }
        return transactionWithCategoriesLinker.linkTransactionsWithCategories(
            transactionsInCurrentPeriod,
            categoriesQueryResult
        )
    }

}