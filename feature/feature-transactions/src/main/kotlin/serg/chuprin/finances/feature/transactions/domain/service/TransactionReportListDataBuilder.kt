package serg.chuprin.finances.feature.transactions.domain.service

import serg.chuprin.finances.core.api.domain.linker.TransactionWithCategoriesLinker
import serg.chuprin.finances.core.api.domain.model.CategoriesQueryResult
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
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

    fun build(
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