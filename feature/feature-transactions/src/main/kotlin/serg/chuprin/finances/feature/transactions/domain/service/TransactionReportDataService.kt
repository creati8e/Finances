package serg.chuprin.finances.feature.transactions.domain.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import serg.chuprin.finances.feature.transactions.domain.builder.TransactionReportChartDataBuilder
import serg.chuprin.finances.feature.transactions.domain.builder.TransactionReportListDataBuilder
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportRawData
import serg.chuprin.finances.feature.transactions.domain.repository.TransactionReportFilterRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 12.12.2020.
 */
class TransactionReportDataService @Inject constructor(
    private val listDataBuilder: TransactionReportListDataBuilder,
    private val filterRepository: TransactionReportFilterRepository,
    private val chartDataBuilder: TransactionReportChartDataBuilder,
    private val categoriesDataService: TransactionReportCategoriesDataService,
    private val transactionDataService: TransactionReportTransactionDataService
) {

    fun buildDataForReport(): Flow<TransactionReportRawData> {
        return flow {
            coroutineScope {

                // distinctUntilChanged here prevents just from duplicate data emissions.
                val filterFlow = filterRepository.filterFlow.distinctUntilChanged()

                // Firstly we need to retrieve categories to associate them with transactions.
                val categoriesFlow = categoriesDataService
                    .categoriesFlow(filterFlow)
                    .share(coroutineScope = this)

                // Then we need to retrieve all transactions for all time which are matches filter.
                // This is required for building historical chart data.
                //
                // Cache flow's to reduce overhead: when current data period in filter changed,
                // we need to retrieve transactions for that data period
                // (preserving chart data and do not rebuild it).
                val transactionsFlow = transactionDataService
                    .transactionsFlow(
                        filterFlow = filterFlow,
                        categoriesFlow = categoriesFlow
                    )
                    .share(coroutineScope = this)

                emitAll(
                    combine(
                        filterFlow,
                        chartDataBuilder.dataFlow(
                            filterFlow = filterFlow,
                            transactionsFlow = transactionsFlow
                        ),
                        listDataBuilder.dataFlow(
                            filterFlow = filterFlow,
                            categoriesFlow = categoriesFlow,
                            transactionsFlow = transactionsFlow
                        ),
                        ::TransactionReportRawData
                    )

                )
            }
        }
    }

    private fun <T> Flow<T>.share(coroutineScope: CoroutineScope): SharedFlow<T> {
        return shareIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(replayExpirationMillis = 0L)
        )
    }

}