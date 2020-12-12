package serg.chuprin.finances.feature.transactions.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.TransactionsByDayGrouper
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.model.transaction.TransactionsQuery
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.domain.service.TransactionCategoryRetrieverService
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportFilter
import serg.chuprin.finances.feature.transactions.domain.model.TransactionsReport
import serg.chuprin.finances.feature.transactions.domain.repository.TransactionReportFilterRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.07.2020.
 */
class BuildTransactionsReportUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val transactionsByDayGrouper: TransactionsByDayGrouper,
    private val filterRepository: TransactionReportFilterRepository,
    private val transactionCategoryRetrieverService: TransactionCategoryRetrieverService
) {

    fun execute(): Flow<TransactionsReport> {
        return filterRepository
            .filterFlow
            .flatMapLatest { filter ->
                combine(
                    flowOf(filter),
                    transactionCategoryRetrieverService.transactionsFlow(
                        createTransactionsQuery(filter)
                    ),
                    ::buildTransactionsReport
                )
            }
    }

    private fun buildTransactionsReport(
        filter: TransactionReportFilter,
        transactions: Map<Transaction, TransactionCategoryWithParent?>
    ): TransactionsReport {
        return TransactionsReport(
            filter = filter,
            transactionsGroupedByDay = transactionsByDayGrouper.group(transactions)
        )
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

}