package serg.chuprin.finances.feature.transactions.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import serg.chuprin.finances.core.api.domain.TransactionAmountCalculator
import serg.chuprin.finances.core.api.domain.TransactionsByDayGrouper
import serg.chuprin.finances.core.api.domain.model.CategoryShares
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.repository.UserRepository
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
    private val transactionsByDayGrouper: TransactionsByDayGrouper,
    private val transactionAmountCalculator: TransactionAmountCalculator
) {

    suspend fun execute(): Flow<TransactionsReport> {
        return combine(
            userRepository.currentUserSingleFlow(),
            reportDataService.dataFlow(),
            ::buildTransactionsReport
        )
    }

    private suspend fun buildTransactionsReport(
        user: User,
        reportRawData: TransactionReportRawData
    ): TransactionsReport {
        val dataPeriodAmount = transactionAmountCalculator.calculate(
            isAbsoluteAmount = false,
            transactions = reportRawData.transactionToCategory.keys
        )
        return TransactionsReport(
            currentUser = user,
            filter = reportRawData.filter,
            preparedData = TransactionReportPreparedData(
                currency = user.defaultCurrency,
                dataPeriodAmount = dataPeriodAmount,
                moneyAccounts = reportRawData.moneyAccounts,
                categorySharesChart = buildCategorySharesChart(user, reportRawData),
                dataPeriodAmounts = calculateAmountsInDataPeriods(reportRawData.dataPeriodAmounts),
                dataPeriodTransactions = transactionsByDayGrouper.group(reportRawData.transactionToCategory)
            )
        )
    }

    private suspend fun buildCategorySharesChart(
        user: User,
        reportRawData: TransactionReportRawData
    ): TransactionReportCategorySharesChart? {

        if (reportRawData.filter is TransactionReportFilter.SingleCategory) {
            return null
        }
        val transactionType = reportRawData.filter.transactionType ?: return null

        val categoryShares = CategoryShares(
            reportRawData
                .categoryToTransactionsList
                .map { (category, transactions) ->
                    Pair(
                        category,
                        transactionAmountCalculator.calculate(transactions, isAbsoluteAmount = true)
                    )
                }
                .sortedByDescending { (_, amount) -> amount }
        )

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

    private suspend fun calculateAmountsInDataPeriods(
        dataPeriodAmounts: Map<DataPeriod, List<Transaction>>
    ): Map<DataPeriod, BigDecimal> {
        return dataPeriodAmounts.mapValues { (_, transactions) ->
            transactionAmountCalculator.calculate(
                isAbsoluteAmount = false,
                transactions = transactions
            )
        }
    }

}