package serg.chuprin.finances.feature.transactions.domain.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.linker.TransactionWithCategoriesLinker
import serg.chuprin.finances.core.api.domain.model.CategoriesQueryResult
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.model.transaction.TransactionsQuery
import serg.chuprin.finances.core.api.domain.repository.TransactionCategoryRepository
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportFilter
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportRawData
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 12.12.2020.
 */
class TransactionReportDataService @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: TransactionCategoryRepository,
    private val queryBuilder: TransactionReportDataServiceQueryBuilder,
    private val transactionWithCategoriesLinker: TransactionWithCategoriesLinker
) {

    /**
     * @return flow emitting map of categories associated with transactions.
     * Children categories are retrieved for each category from [TransactionsQuery.categoryIds].
     */
    suspend fun buildDataForReport(
        filter: TransactionReportFilter
    ): Flow<TransactionReportRawData> {
        /**
         * Get categories first and retrieve children categories
         * from [TransactionsQuery.categoryIds] and then observe transactions.
         */
        return combine(
            flowOf(filter),
            categoryRepository.categoriesFlow(queryBuilder.buildForCategories(filter)),
            ::Pair
        ).flatMapLatest { (filter1, categories) ->
            combine(
                flowOf(filter1),
                flowOf(categories),
                getTransactions(categories, filter1),
                ::buildDataSet
            )
        }
    }

    private fun buildDataSet(
        filter: TransactionReportFilter,
        categoriesQueryResult: CategoriesQueryResult,
        transactions: List<Transaction>
    ): TransactionReportRawData {
        return TransactionReportRawData(
            chartData = buildDataForChart(transactions, categoriesQueryResult),
            listData = buildDataForList(transactions, categoriesQueryResult, filter)
        )
    }

    private fun buildDataForList(
        transactions: List<Transaction>,
        categoriesQueryResult: CategoriesQueryResult,
        filter: TransactionReportFilter
    ): Map<Transaction, TransactionCategoryWithParent?> {
        val transactionsInCurrentPeriod = transactions.filter { transaction ->
            transaction.dateTime in filter.dataPeriod
        }
        return transactionWithCategoriesLinker.linkTransactionsWithCategories(
            transactionsInCurrentPeriod,
            categoriesQueryResult
        )
    }

    private fun buildDataForChart(
        transactions: List<Transaction>,
        categoriesQueryResult: CategoriesQueryResult
    ): Map<TransactionCategory?, List<Transaction>> {
        return transactionWithCategoriesLinker.linkCategoryParentsWithTransactions(
            transactions,
            categoriesQueryResult
        )
    }

    private suspend fun getTransactions(
        categoriesQueryResult: CategoriesQueryResult,
        filter: TransactionReportFilter
    ): Flow<List<Transaction>> {
        val query = queryBuilder.buildForTransactions(filter, categoriesQueryResult)
        return transactionRepository.transactionsFlow(query)
    }

}