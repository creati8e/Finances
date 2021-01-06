package serg.chuprin.finances.feature.transactions.domain.builder

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.period.DataPeriodType
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.extensions.buildSortedMap
import serg.chuprin.finances.core.api.extensions.flow.distinctUntilChangedBy
import serg.chuprin.finances.feature.transactions.domain.model.ReportDataPeriod
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportFilter
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 21.12.2020.
 *
 * This builder creates data for displaying on data period amounts chart:
 * it puts transactions in each period starting from youngest to oldest.
 */
class TransactionReportDataPeriodAmountsBuilder @Inject constructor() {

    private companion object {

        private val INTERESTED_KEYS: List<((TransactionReportFilter) -> Any?)> = listOf(

            /**
             * We want to rebuild chart data if report period type has changed
             * i.e from [ReportDataPeriod.Predefined] to [ReportDataPeriod.Custom].
             *
             * @see [buildData]
             */
            { filter -> filter.reportDataPeriod::class.java },

            /**
             * We want to rebuild chart data if data period type has changed
             * i.e from [DataPeriodType.DAY] to [DataPeriodType.MONTH].
             *
             * @see [buildData]
             */
            { filter -> filter.reportDataPeriod.dataPeriod?.periodType }
        )

        private val dataPeriodComparator = Comparator<DataPeriod> { period1, period2 ->
            period1.startDate.compareTo(period2.startDate)
        }

    }

    fun dataFlow(
        filterFlow: Flow<TransactionReportFilter>,
        transactionsFlow: Flow<List<Transaction>>
    ): Flow<Map<DataPeriod, List<Transaction>>> {
        return combine(
            transactionsFlow,
            filterFlow.distinctUntilChangedBy(INTERESTED_KEYS),
            ::buildData
        )
    }

    private fun buildData(
        transactions: List<Transaction>,
        filter: TransactionReportFilter
    ): Map<DataPeriod, List<Transaction>> {
        if (transactions.isEmpty()) {
            return emptyMap()
        }
        return when (val reportDataPeriod = filter.reportDataPeriod) {
            ReportDataPeriod.AllTime, is ReportDataPeriod.Custom -> emptyMap()
            is ReportDataPeriod.Predefined -> {
                when (filter) {
                    is TransactionReportFilter.SingleCategory -> {
                        buildForPredefinedDataPeriod(reportDataPeriod.dataPeriod, transactions)
                    }
                    is TransactionReportFilter.Plain -> {
                        if (filter.transactionType != null) {
                            buildForPredefinedDataPeriod(reportDataPeriod.dataPeriod, transactions)
                        } else {
                            emptyMap()
                        }
                    }
                    is TransactionReportFilter.Categories -> emptyMap()
                }
            }
        }
    }

    private fun buildForPredefinedDataPeriod(
        dataPeriod: DataPeriod,
        transactions: List<Transaction>
    ): Map<DataPeriod, List<Transaction>> {

        // Transactions are sorted here so it's convenient to take first and last.
        val endDataPeriod = DataPeriod.fromEndDate(
            endDate = maxOf(
                transactions.last().dateTime,
                LocalDateTime.now()
            ),
            periodType = dataPeriod.periodType
        )

        val startDataPeriod = DataPeriod
            .fromStartDate(
                periodType = dataPeriod.periodType,
                startDate = transactions.first().dateTime
            )
            .minusPeriods(5)

        return buildDataPeriods(startDataPeriod, endDataPeriod, transactions)
    }

    /**
     * Function that creates range from [startDataPeriod] to [endDataPeriod]
     * and puts transactions into each intermediate [DataPeriod] without
     * skipping intermediate periods.
     *
     * Periods in resulting map are sorted from youngest to oldest.
     */
    private fun buildDataPeriods(
        startDataPeriod: DataPeriod,
        endDataPeriod: DataPeriod,
        transactions: List<Transaction>
    ): Map<DataPeriod, List<Transaction>> {
        return buildSortedMap(dataPeriodComparator) {
            for (currentDataPeriod in (startDataPeriod..endDataPeriod)) {
                put(
                    currentDataPeriod,
                    transactions.filter { it.dateTime in currentDataPeriod }
                )
            }
        }
    }

}