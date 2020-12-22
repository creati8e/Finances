package serg.chuprin.finances.feature.transactions.domain.service

import serg.chuprin.finances.core.api.domain.model.CategoriesQueryResult
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryType
import serg.chuprin.finances.core.api.domain.model.query.TransactionCategoriesQuery
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.domain.model.transaction.TransactionsQuery
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.feature.transactions.domain.model.ReportDataPeriod
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportFilter
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 21.12.2020.
 */
class TransactionReportDataServiceQueryBuilder @Inject constructor(
    private val userRepository: UserRepository
) {

    companion object {

        val CATEGORY_INTERESTED_KEYS: List<((TransactionReportFilter) -> Any?)> = listOf(
            TransactionReportFilter::categoryIds,
            TransactionReportFilter::transactionType
        )

        val TRANSACTION_INTERESTED_KEYS: List<((TransactionReportFilter) -> Any?)> = listOf(
            TransactionReportFilter::reportDataPeriod,
            TransactionReportFilter::transactionType
        )

    }

    suspend fun buildForCategories(
        filter: TransactionReportFilter
    ): TransactionCategoriesQuery {
        // FIXME: Unify maybe.
        val categoryType = when (filter.transactionType) {
            PlainTransactionType.INCOME -> TransactionCategoryType.INCOME
            PlainTransactionType.EXPENSE -> TransactionCategoryType.EXPENSE
            null -> null
        }
        return TransactionCategoriesQuery(
            type = categoryType,
            categoryIds = filter.categoryIds,
            userId = userRepository.getCurrentUser().id,
            relation = TransactionCategoriesQuery.Relation.RETRIEVE_CHILDREN
        )
    }

    suspend fun buildForTransactions(
        filter: TransactionReportFilter,
        categories: CategoriesQueryResult
    ): TransactionsQuery {
        // If report has non-custom and non-all-time period type,
        // get data for all periods from min to max. This data is required for chart building.
        val (startDate, endDate) = when (val reportDataPeriod = filter.reportDataPeriod) {
            ReportDataPeriod.AllTime, is ReportDataPeriod.Predefined -> null to null
            is ReportDataPeriod.Custom -> {
                reportDataPeriod.startDate to reportDataPeriod.endDate
            }
        }
        return TransactionsQuery(
            endDate = endDate,
            startDate = startDate,
            transactionType = filter.transactionType,
            userId = userRepository.getCurrentUser().id,
            sortOrder = TransactionsQuery.SortOrder.DATE_ASC,
            categoryIds = categories.mapTo(mutableSetOf(), { (categoryId) -> categoryId })
        )
    }
}