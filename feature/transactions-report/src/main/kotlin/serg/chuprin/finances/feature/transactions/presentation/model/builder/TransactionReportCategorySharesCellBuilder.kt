package serg.chuprin.finances.feature.transactions.presentation.model.builder

import serg.chuprin.finances.core.api.R
import serg.chuprin.finances.core.api.domain.model.CategoryShares
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.formatter.CategoryColorFormatter
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.piechart.model.PieChartDataPart
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportCategorySharesChart
import serg.chuprin.finances.feature.transactions.presentation.model.cells.TransactionReportCategoryShareCell
import serg.chuprin.finances.feature.transactions.presentation.model.cells.TransactionReportCategorySharesCell
import java.util.*
import javax.inject.Inject
import serg.chuprin.finances.core.piechart.R as PieChartR

/**
 * Created by Sergey Chuprin on 24.12.2020.
 */
class TransactionReportCategorySharesCellBuilder @Inject constructor(
    private val resourceManger: ResourceManger,
    private val amountFormatter: AmountFormatter,
    private val categoryColorFormatter: CategoryColorFormatter
) {

    fun build(
        chart: TransactionReportCategorySharesChart,
    ): TransactionReportCategorySharesCell {
        return TransactionReportCategorySharesCell(
            label = formatLabel(chart),
            transactionType = chart.transactionType,
            chartParts = buildChartParts(chart.categoryShares),
            categoryCells = buildCategoryShareCells(
                currency = chart.currency,
                categoryShares = chart.categoryShares,
                transactionType = chart.transactionType
            ),
            totalAmount = amountFormatter.format(
                currency = chart.currency,
                amount = chart.totalAmount
            )
        )
    }

    private fun buildChartParts(
        categoryShares: CategoryShares
    ): List<PieChartDataPart> {
        if (categoryShares.isEmpty()) {
            return listOf(
                PieChartDataPart(
                    value = 1f,
                    colorInt = getEmptyChartPartColor()
                )
            )
        }
        return categoryShares.mapTo(mutableListOf()) { (category, amount) ->
            PieChartDataPart(
                value = amount.toFloat(),
                colorInt = categoryColorFormatter.format(category)
            )
        }
    }

    private fun buildCategoryShareCells(
        categoryShares: CategoryShares,
        currency: Currency,
        transactionType: PlainTransactionType
    ): List<BaseCell> {
        if (categoryShares.isEmpty()) {
            return emptyList()
        }
        return categoryShares
            .mapTo(mutableListOf()) { (category, amount) ->

                val categoryName = category?.name ?: resourceManger.getString(R.string.no_category)
                val text = "$categoryName ${amountFormatter.format(amount, currency)}"

                TransactionReportCategoryShareCell(
                    text = text,
                    category = category,
                    plainTransactionType = transactionType,
                    colorInt = categoryColorFormatter.format(category)
                )
            }
    }

    private fun formatLabel(chart: TransactionReportCategorySharesChart): String {
        return when (chart.transactionType) {
            PlainTransactionType.INCOME -> resourceManger.getString(R.string.income)
            PlainTransactionType.EXPENSE -> resourceManger.getString(R.string.expenses)
        }
    }

    private fun getEmptyChartPartColor(): Int {
        return resourceManger.getColor(PieChartR.color.colorEmptyChartPart)
    }

}