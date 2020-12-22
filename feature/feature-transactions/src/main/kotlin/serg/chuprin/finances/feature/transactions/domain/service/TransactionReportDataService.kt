package serg.chuprin.finances.feature.transactions.domain.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import serg.chuprin.finances.core.api.domain.model.CategoriesQueryResult
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.model.transaction.TransactionsQuery
import serg.chuprin.finances.core.api.domain.repository.TransactionCategoryRepository
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportFilter
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportRawData
import serg.chuprin.finances.feature.transactions.domain.repository.TransactionReportFilterRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 12.12.2020.
 */
class TransactionReportDataService @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: TransactionCategoryRepository,
    private val listDataBuilder: TransactionReportListDataBuilder,
    private val filterRepository: TransactionReportFilterRepository,
    private val chartDataBuilder: TransactionReportChartDataBuilder,
    private val queryBuilder: TransactionReportDataServiceQueryBuilder
) {

    /**
     * @return flow emitting map of categories associated with transactions.
     * Children categories are retrieved for each category from [TransactionsQuery.categoryIds].
     */
    fun buildDataForReport(): Flow<TransactionReportRawData> {
        return flow {
            coroutineScope {

                // TODO: Distinct by parameters.

                val sharedFilterFlow = filterRepository.filterFlow

                val sharedCategoriesFlow = sharedFilterFlow
                    .filterForCategories()
                    .flatMapLatest { filter ->
                        categoryRepository.categoriesFlow(
                            queryBuilder.buildForCategories(filter)
                        )
                    }
                    .share(coroutineScope = this)

                val sharedTransactionsFlow = combine(
                    sharedFilterFlow.filterForTransactions(),
                    sharedCategoriesFlow,
                    ::Pair
                ).flatMapLatest { (filter, categories) ->
                    getTransactions(categories, filter)
                }.share(coroutineScope = this)

                val chartDataFlow = combine(
                    sharedTransactionsFlow,
                    sharedFilterFlow.filterForTransactions(),
                    chartDataBuilder::build
                )

                val listDataFlow = combine(
                    sharedTransactionsFlow,
                    sharedCategoriesFlow,
                    sharedFilterFlow,
                    listDataBuilder::build
                )

                emitAll(
                    combine(
                        sharedFilterFlow,
                        chartDataFlow,
                        listDataFlow,
                        ::TransactionReportRawData
                    )
                )

            }
        }
    }

    private fun Flow<TransactionReportFilter>.filterForCategories(): Flow<TransactionReportFilter> {
        return this
//        return distinctUntilChangedBy(
//            TransactionReportDataServiceQueryBuilder.CATEGORY_INTERESTED_KEYS
//        )
    }

    private fun Flow<TransactionReportFilter>.filterForTransactions(): Flow<TransactionReportFilter> {
        return this
//        return distinctUntilChangedBy(
//            TransactionReportDataServiceQueryBuilder.TRANSACTION_INTERESTED_KEYS
//        )
    }

    private suspend fun getTransactions(
        categoriesQueryResult: CategoriesQueryResult,
        filter: TransactionReportFilter
    ): Flow<List<Transaction>> {
        val query = queryBuilder.buildForTransactions(filter, categoriesQueryResult)
        return transactionRepository.transactionsFlow(query)
    }

    private fun <T> Flow<T>.share(coroutineScope: CoroutineScope): SharedFlow<T> {
        return shareIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(replayExpirationMillis = 0L)
        )
    }

}