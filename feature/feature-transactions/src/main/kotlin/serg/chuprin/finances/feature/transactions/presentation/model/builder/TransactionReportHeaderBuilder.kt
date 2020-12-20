package serg.chuprin.finances.feature.transactions.presentation.model.builder

import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.feature.transactions.R
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportFilter
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportPreparedData
import serg.chuprin.finances.feature.transactions.domain.model.TransactionsReport
import serg.chuprin.finances.feature.transactions.presentation.model.TransactionReportHeader
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 13.12.2020.
 */
class TransactionReportHeaderBuilder @Inject constructor(
    private val resourceManger: ResourceManger
) {

    fun build(report: TransactionsReport): TransactionReportHeader {
        // TODO: Add formatting for all filter types.
        val title = when (val filter = report.filter) {
            is TransactionReportFilter.Plain -> {
                filter.transactionType?.format()
                    ?: resourceManger.getString(R.string.transactions_report_toolbar_title)
            }
            is TransactionReportFilter.SingleCategory -> {
                buildTitleForSingleCategory(filter, report.preparedData)
            }
            is TransactionReportFilter.Categories -> {
                filter.transactionType.format()
            }
        }
        return TransactionReportHeader(title)
    }

    private fun buildTitleForSingleCategory(
        filter: TransactionReportFilter.SingleCategory,
        preparedData: TransactionReportPreparedData
    ): String {
        val chartData = preparedData.chartData
        if (chartData.isEmpty()) {
            return filter.transactionType.format()
        }

        // Find parent category.
        val category = chartData.entries
            .find { (category) -> category?.parentCategoryId == null }
            ?.key

        return category?.name ?: filter.transactionType.format()
    }

    private fun PlainTransactionType.format(): String {
        val stringRes = when (this) {
            PlainTransactionType.INCOME -> R.string.incomes
            PlainTransactionType.EXPENSE -> R.string.expenses
        }
        return resourceManger.getString(stringRes)
    }

}