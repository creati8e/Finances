package serg.chuprin.finances.feature.transactions.domain.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.linker.TransactionWithCategoriesLinker
import serg.chuprin.finances.core.api.domain.model.CategoriesQueryResult
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryType
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.query.TransactionCategoriesQuery
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.model.transaction.TransactionsQuery
import serg.chuprin.finances.core.api.domain.repository.TransactionCategoryRepository
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportDataSet
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 12.12.2020.
 */
class TransactionReportDataService @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: TransactionCategoryRepository,
    private val transactionWithCategoriesLinker: TransactionWithCategoriesLinker
) {

    /**
     * @return flow emitting map of categories associated with transactions.
     * Children categories are retrieved for each category from [TransactionsQuery.categoryIds].
     */
    fun buildDataForReport(
        query: TransactionsQuery
    ): Flow<TransactionReportDataSet> {

        // FIXME: Unify.
        val categoryType = when (query.transactionType) {
            PlainTransactionType.INCOME -> TransactionCategoryType.INCOME
            PlainTransactionType.EXPENSE -> TransactionCategoryType.EXPENSE
            null -> null
        }

        /**
         * Get categories first and retrieve children categories
         * from [TransactionsQuery.categoryIds] and then observe transactions.
         */
        return categoryRepository
            .categoriesFlow(
                TransactionCategoriesQuery(
                    type = categoryType,
                    userId = query.userId,
                    categoryIds = query.categoryIds.filterNotNullTo(mutableSetOf()),
                    relation = TransactionCategoriesQuery.Relation.RETRIEVE_CHILDREN
                )
            )
            .flatMapLatest { categories ->
                combine(
                    flowOf(categories),
                    getTransactions(query, categories),
                    ::buildDataSet
                )
            }
    }

    private fun buildDataSet(
        categoryWithParentMap: CategoriesQueryResult,
        transactions: List<Transaction>
    ): TransactionReportDataSet {
        return TransactionReportDataSet(
            chartData = buildDataForChart(transactions, categoryWithParentMap),
            listData = buildDataForList(transactions, categoryWithParentMap)
        )
    }

    private fun getTransactions(
        originalQuery: TransactionsQuery,
        categories: CategoriesQueryResult
    ): Flow<List<Transaction>> {
        val transactionQueryWithChildrenCategories = originalQuery.copy(
            categoryIds = categories.mapTo(mutableSetOf(), { (categoryId) -> categoryId })
        )
        return transactionRepository.transactionsFlow(transactionQueryWithChildrenCategories)
    }

    private fun buildDataForList(
        transactions: List<Transaction>,
        categoryWithParentMap: CategoriesQueryResult
    ): Map<Transaction, TransactionCategoryWithParent?> {
        return transactionWithCategoriesLinker.linkTransactionsWithCategories(
            transactions,
            categoryWithParentMap
        )
    }

    private fun buildDataForChart(
        transactions: List<Transaction>,
        categoryWithParentMap: CategoriesQueryResult
    ): Map<TransactionCategory?, List<Transaction>> {
        return transactionWithCategoriesLinker.linkCategoryParentsWithTransactions(
            transactions,
            categoryWithParentMap
        )
    }


}