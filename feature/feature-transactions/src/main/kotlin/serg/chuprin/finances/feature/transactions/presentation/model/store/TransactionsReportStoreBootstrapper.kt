package serg.chuprin.finances.feature.transactions.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.domain.model.period.ReportDataPeriod
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import serg.chuprin.finances.feature.transactions.domain.model.TransactionsReportFilter
import serg.chuprin.finances.feature.transactions.domain.repository.TransactionReportFilterRepository
import serg.chuprin.finances.feature.transactions.domain.usecase.BuildTransactionsReportUseCase
import serg.chuprin.finances.feature.transactions.presentation.arguments.TransactionsReportInitialFilter
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 12.05.2020.
 */
class TransactionsReportStoreBootstrapper @Inject constructor(
    private val initialFilter: TransactionsReportInitialFilter,
    private val buildTransactionsReportUseCase: BuildTransactionsReportUseCase,
    private val transactionReportFilterRepository: TransactionReportFilterRepository
) : StoreBootstrapper<TransactionsReportAction> {

    override fun invoke(): Flow<TransactionsReportAction> {
        val actualFilter = TransactionsReportFilter(
            includedCategoryIds = initialFilter.categoryIds,
            transactionType = initialFilter.plainTransactionType,
            dataPeriod = initialFilter.dataPeriod
                ?.let(ReportDataPeriod::Predefined)
                ?: ReportDataPeriod.AllTime
        )
        transactionReportFilterRepository.updateFilter(actualFilter)
        return buildTransactionsReportUseCase
            .execute()
            .map { report -> TransactionsReportAction.FormatReport(report) }
    }

}