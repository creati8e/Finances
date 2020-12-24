package serg.chuprin.finances.feature.transactions.domain.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import serg.chuprin.finances.feature.transactions.domain.builder.TransactionReportDataPeriodAmountsBuilder
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
    private val categoriesDataService: TransactionReportCategoriesDataService,
    private val transactionDataService: TransactionReportTransactionDataService,
    private val dataPeriodAmountsBuilder: TransactionReportDataPeriodAmountsBuilder
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
                // This is required for building historical data period amounts.
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
                        dataPeriodAmountsBuilder.dataFlow(
                            filterFlow = filterFlow,
                            transactionsFlow = transactionsFlow
                        ),
                        // Filter and list data emissions must be zipped because list data is updated
                        // when filter is updated. This is required for preventing simultaneous emissions.
                        listDataBuilder
                            .dataFlow(
                                filterFlow = filterFlow,
                                categoriesFlow = categoriesFlow,
                                transactionsFlow = transactionsFlow
                            )
                            .zip(filterFlow, ::Pair)
                    ) { dataPeriodAmounts, (transactions, filter) ->
                        TransactionReportRawData(
                            filter = filter,
                            listData = transactions,
                            dataPeriodAmounts = dataPeriodAmounts
                        )
                    }
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