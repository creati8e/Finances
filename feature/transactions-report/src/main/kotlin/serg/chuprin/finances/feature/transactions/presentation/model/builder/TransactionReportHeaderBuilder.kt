package serg.chuprin.finances.feature.transactions.presentation.model.builder

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.category.query.CategoriesQuery
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.domain.repository.CategoryRepository
import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.feature.transactions.R
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportFilter
import serg.chuprin.finances.feature.transactions.domain.model.TransactionsReport
import serg.chuprin.finances.feature.transactions.presentation.model.TransactionReportHeader
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 13.12.2020.
 */
class TransactionReportHeaderBuilder @Inject constructor(
    private val resourceManger: ResourceManger,
    private val categoryRepository: CategoryRepository
) {

    suspend fun build(report: TransactionsReport): TransactionReportHeader {
        val filter = report.filter

        val title = when (filter) {
            is TransactionReportFilter.Plain -> {
                filter.transactionType?.format()
                    ?: resourceManger.getString(R.string.transactions_report_toolbar_title)
            }
            is TransactionReportFilter.SingleCategory -> {
                buildTitleForSingleCategory(filter, report.currentUser)
            }
            is TransactionReportFilter.Categories -> {
                filter.transactionType.format()
            }
        }

        val subtitle = when (filter) {
            is TransactionReportFilter.Plain -> {
                EMPTY_STRING
            }
            is TransactionReportFilter.SingleCategory -> {
                filter.transactionType.format()
            }
            is TransactionReportFilter.Categories -> {
                EMPTY_STRING
            }
        }
        return TransactionReportHeader(title = title, subtitle = subtitle)
    }

    private suspend fun buildTitleForSingleCategory(
        filter: TransactionReportFilter.SingleCategory,
        currentUser: User
    ): String {
        if (filter.categoryId == Id.UNKNOWN) {
            return resourceManger.getString(R.string.no_category)
        }
        val category = categoryRepository
            .categories(
                CategoriesQuery(
                    type = null,
                    relation = null,
                    ownerId = currentUser.id,
                    categoryIds = setOf(filter.categoryId)
                )
            )
            .values
            .firstOrNull()
            ?.category

        return category?.name ?: filter.transactionType.format()
    }

    private fun PlainTransactionType.format(): String {
        val stringRes = when (this) {
            PlainTransactionType.INCOME -> R.string.income
            PlainTransactionType.EXPENSE -> R.string.expenses
        }
        return resourceManger.getString(stringRes)
    }

}