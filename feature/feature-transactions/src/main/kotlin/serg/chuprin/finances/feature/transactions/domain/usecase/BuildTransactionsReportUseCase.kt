package serg.chuprin.finances.feature.transactions.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.TransactionsByDayGrouper
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.extensions.amount
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportFilter
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportPreparedData
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportRawData
import serg.chuprin.finances.feature.transactions.domain.model.TransactionsReport
import serg.chuprin.finances.feature.transactions.domain.repository.TransactionReportFilterRepository
import serg.chuprin.finances.feature.transactions.domain.service.TransactionReportDataService
import java.math.BigDecimal
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.07.2020.
 */
class BuildTransactionsReportUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val reportDataService: TransactionReportDataService,
    private val transactionsByDayGrouper: TransactionsByDayGrouper,
    private val filterRepository: TransactionReportFilterRepository
) {

    fun execute(): Flow<TransactionsReport> {
        return filterRepository
            .filterFlow
            .flatMapLatest { filter ->
                combine(
                    flowOf(filter),
                    userRepository.currentUserSingleFlow(),
                    reportDataService.buildDataForReport(filter),
                    ::buildTransactionsReport
                )
            }
    }

    private fun buildTransactionsReport(
        filter: TransactionReportFilter,
        user: User,
        reportRawData: TransactionReportRawData
    ): TransactionsReport {
        return TransactionsReport(
            filter = filter,
            preparedData = TransactionReportPreparedData(
                currency = user.defaultCurrency,
                chartData = calculateAmountsInDataPeriods(reportRawData.chartData),
                transactionsGroupedByDay = transactionsByDayGrouper.group(reportRawData.listData)
            )
        )
    }

    private fun calculateAmountsInDataPeriods(
        chartData: Map<DataPeriod, List<Transaction>>
    ): Map<DataPeriod, BigDecimal> {
        return chartData.mapValues { (_, transactions) -> transactions.amount }
    }

}