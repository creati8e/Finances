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
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportFilter
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportRawData
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 12.12.2020.
 */
class TransactionReportDataService @Inject constructor(
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: TransactionCategoryRepository,
    private val transactionWithCategoriesLinker: TransactionWithCategoriesLinker
) {

    /**
     * @return flow emitting map of categories associated with transactions.
     * Children categories are retrieved for each category from [TransactionsQuery.categoryIds].
     */
    suspend fun buildDataForReport(
        filter: TransactionReportFilter
    ): Flow<TransactionReportRawData> {

        val transactionsQuery = createTransactionsQuery(filter)

        return combine(
            flowOf(transactionsQuery),
            /**
             * Get categories first and retrieve children categories
             * from [TransactionsQuery.categoryIds] and then observe transactions.
             */
            categoryRepository.categoriesFlow(createCategoriesQuery(transactionsQuery)),
            ::Pair
        ).flatMapLatest { (query, categories) ->
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
    ): TransactionReportRawData {
        return TransactionReportRawData(
            chartData = buildDataForChart(transactions, categoryWithParentMap),
            listData = buildDataForList(transactions, categoryWithParentMap)
        )
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

    private fun getTransactions(
        originalQuery: TransactionsQuery,
        categories: CategoriesQueryResult
    ): Flow<List<Transaction>> {
        val transactionQueryWithChildrenCategories = originalQuery.copy(
            categoryIds = categories.mapTo(mutableSetOf(), { (categoryId) -> categoryId })
        )
        return transactionRepository.transactionsFlow(transactionQueryWithChildrenCategories)
    }

    private suspend fun createTransactionsQuery(
        filter: TransactionReportFilter
    ): TransactionsQuery {
        return TransactionsQuery(
            categoryIds = filter.categoryIds,
            endDate = filter.dataPeriod.endDate,
            startDate = filter.dataPeriod.startDate,
            transactionType = filter.transactionType,
            userId = userRepository.getCurrentUser().id
        )
    }

    private fun createCategoriesQuery(
        transactionsQuery: TransactionsQuery
    ): TransactionCategoriesQuery {
        // FIXME: Unify.
        val categoryType = when (transactionsQuery.transactionType) {
            PlainTransactionType.INCOME -> TransactionCategoryType.INCOME
            PlainTransactionType.EXPENSE -> TransactionCategoryType.EXPENSE
            null -> null
        }
        return TransactionCategoriesQuery(
            type = categoryType,
            userId = transactionsQuery.userId,
            relation = TransactionCategoriesQuery.Relation.RETRIEVE_CHILDREN,
            categoryIds = transactionsQuery.categoryIds.filterNotNullTo(mutableSetOf())
        )
    }

}