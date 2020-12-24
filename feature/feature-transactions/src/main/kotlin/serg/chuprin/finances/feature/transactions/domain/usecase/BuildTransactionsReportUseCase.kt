package serg.chuprin.finances.feature.transactions.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import serg.chuprin.finances.core.api.domain.TransactionsByDayGrouper
import serg.chuprin.finances.core.api.domain.model.TransactionCategoryShares
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.extensions.amount
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportPreparedData
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportRawData
import serg.chuprin.finances.feature.transactions.domain.model.TransactionsReport
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
            filter = reportRawData.filter,
            preparedData = TransactionReportPreparedData(
                currency = user.defaultCurrency,
                dataPeriodAmount = reportRawData.listData.keys.amount,
                categoryShares = calculateCategoryShares(reportRawData.categoryTransactions),
                dataPeriodTransactions = transactionsByDayGrouper.group(reportRawData.listData),
                dataPeriodAmounts = calculateAmountsInDataPeriods(reportRawData.dataPeriodAmounts)
            )
        )
    }

    private fun calculateCategoryShares(
        categoryTransactions: Map<TransactionCategory?, List<Transaction>>
    ): TransactionCategoryShares {
        return categoryTransactions
            .mapValues { (_, transactions) -> transactions.amount }
            .let(::TransactionCategoryShares)
    }

    private fun calculateAmountsInDataPeriods(
        dataPeriodAmounts: Map<DataPeriod, List<Transaction>>
    ): Map<DataPeriod, BigDecimal> {
        return dataPeriodAmounts.mapValues { (_, transactions) -> transactions.amount }
    }

}