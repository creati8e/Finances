package serg.chuprin.finances.feature.transactions.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import serg.chuprin.finances.core.api.domain.TransactionsByDayGrouper
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.extensions.amount
import serg.chuprin.finances.feature.transactions.domain.model.*
import serg.chuprin.finances.feature.transactions.domain.service.TransactionReportDataService
import java.math.BigDecimal
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.07.2020.
 */
class BuildTransactionsReportUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val reportDataService: TransactionReportDataService,
    private val transactionsByDayGrouper: TransactionsByDayGrouper
) {

    fun execute(): Flow<TransactionsReport> {
        return combine(
            userRepository.currentUserSingleFlow(),
            reportDataService.dataFlow(),
            ::buildTransactionsReport
        )
    }

    private fun buildTransactionsReport(
        user: User,
        reportRawData: TransactionReportRawData
    ): TransactionsReport {
        return TransactionsReport(
            currentUser = user,
            filter = reportRawData.filter,
            preparedData = TransactionReportPreparedData(
                currency = user.defaultCurrency,
                moneyAccounts = reportRawData.moneyAccounts,
                dataPeriodAmount = reportRawData.transactionCategories.keys.amount,
                categorySharesChart = buildCategorySharesChart(user, reportRawData),
                dataPeriodTransactions = transactionsByDayGrouper.group(reportRawData.transactionCategories),
                dataPeriodAmounts = calculateAmountsInDataPeriods(reportRawData.dataPeriodAmounts)
            )
        )
    }

    private fun buildCategorySharesChart(
        user: User,
        reportRawData: TransactionReportRawData
    ): TransactionReportCategorySharesChart? {

        if (reportRawData.filter is TransactionReportFilter.SingleCategory) {
            return null
        }
        val transactionType = reportRawData.filter.transactionType ?: return null

        val categoryShares = reportRawData
            .categoryToTransactionsList
            .map { (category, transactions) -> category to transactions.amount.abs() }
            .sortedByDescending { (_, amount) -> amount }

        val totalAmount = categoryShares.run {
            var amount = BigDecimal.ZERO
            forEach { (_, categoryAmount) ->
                amount += categoryAmount
            }
            amount
        }

        return TransactionReportCategorySharesChart(
            totalAmount = totalAmount,
            categoryShares = categoryShares,
            currency = user.defaultCurrency,
            transactionType = transactionType
        )
    }

    private fun calculateAmountsInDataPeriods(
        dataPeriodAmounts: Map<DataPeriod, List<Transaction>>
    ): Map<DataPeriod, BigDecimal> {
        return dataPeriodAmounts.mapValues { (_, transactions) -> transactions.amount }
    }

}