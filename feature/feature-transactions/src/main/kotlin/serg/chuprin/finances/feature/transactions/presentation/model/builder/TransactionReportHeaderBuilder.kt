package serg.chuprin.finances.feature.transactions.presentation.model.builder

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.query.TransactionCategoriesQuery
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.domain.repository.TransactionCategoryRepository
import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.feature.transactions.R
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportFilter
import serg.chuprin.finances.feature.transactions.domain.model.TransactionsReport
import serg.chuprin.finances.feature.transactions.presentation.model.TransactionReportHeader
import serg.chuprin.finances.feature.transactions.presentation.model.cells.TransactionReportChartCell
import serg.chuprin.finances.feature.transactions.presentation.model.cells.TransactionReportChartListCell
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 13.12.2020.
 */
class TransactionReportHeaderBuilder @Inject constructor(
    private val resourceManger: ResourceManger,
    private val categoryRepository: TransactionCategoryRepository,
    private val chartCellsBuilder: TransactionReportChartCellsBuilder
) {

    suspend fun build(report: TransactionsReport): TransactionReportHeader {
        val preparedData = report.preparedData
        val chartCells = chartCellsBuilder.build(
            currency = preparedData.currency,
            dataPeriodAmounts = preparedData.dataPeriodAmounts,
            currentDataPeriod = report.filter.reportDataPeriod.dataPeriod
        )

        val filter = report.filter

        // TODO: Add formatting for all filter types.
        val title = when (filter) {
            is TransactionReportFilter.Plain -> {
                filter.transactionType?.format()
                    ?: resourceManger.getString(R.string.transactions_report_toolbar_title)
            }
            is TransactionReportFilter.SingleCategory -> {
                buildTitleForSingleCategory(filter)
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
                buildSubtitleForSingleCategory(filter)
            }
            is TransactionReportFilter.Categories -> {
                EMPTY_STRING
            }
        }

        return TransactionReportHeader(
            title = title,
            subtitle = subtitle,
            chartListCell = buildTransactionReportChartListCell(chartCells)
        )
    }

    private fun buildTransactionReportChartListCell(
        chartCells: List<TransactionReportChartCell>
    ): List<TransactionReportChartListCell> {
        if (chartCells.isEmpty()) {
            return emptyList()
        }
        return listOf(TransactionReportChartListCell(chartCells))
    }

    private fun buildSubtitleForSingleCategory(
        filter: TransactionReportFilter.SingleCategory
    ): String {
        return filter.transactionType.format()
    }

    private suspend fun buildTitleForSingleCategory(
        filter: TransactionReportFilter.SingleCategory
    ): String {
        if (filter.categoryId == Id.UNKNOWN) {
            return resourceManger.getString(R.string.no_category)
        }
        val category = categoryRepository
            .categories(
                TransactionCategoriesQuery(
                    type = null,
                    userId = null,
                    relation = null,
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
            PlainTransactionType.INCOME -> R.string.incomes
            PlainTransactionType.EXPENSE -> R.string.expenses
        }
        return resourceManger.getString(stringRes)
    }

}