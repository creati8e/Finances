package serg.chuprin.finances.feature.dashboard.presentation.model.builder

import android.graphics.Color
import androidx.annotation.ColorInt
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.presentation.model.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.piechart.model.PieChartDataPart
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardCategoryChipCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import javax.inject.Inject
import serg.chuprin.finances.core.api.R as CoreR

/**
 * Created by Sergey Chuprin on 24.04.2020.
 */
class DashboardCategoriesWidgetCellBuilder @Inject constructor(
    private val resourceManger: ResourceManger,
    private val amountFormatter: AmountFormatter
) : DashboardWidgetCellBuilder {

    override fun build(widget: DashboardWidget): DashboardWidgetCell? {
        if (widget !is DashboardWidget.Categories) {
            return null
        }

        val chartParts = widget.categoryAmounts.entries.map { (category, amount) ->
            PieChartDataPart(
                value = amount.toFloat(),
                colorInt = getCategoryColor(category)
            )
        }

        val categoryCells = widget.categoryAmounts.entries.map { (category, amount) ->
            val categoryName = category?.name ?: resourceManger.getString(CoreR.string.no_category)
            val chipText = "$categoryName ${amountFormatter.format(amount, widget.currency)}"
            DashboardCategoryChipCell(
                chipText = chipText,
                category = category,
                colorInt = getCategoryColor(category)
            )
        }

        // TODO:
        return DashboardWidgetCell.Categories(
            widget = widget,
            chartParts = chartParts,
            categoryCells = categoryCells,
            transactionsType = "Expense",
            totalAmount = amountFormatter.format(
                currency = widget.currency,
                amount = widget.totalAmount
            )
        )
    }

    @ColorInt
    private fun getCategoryColor(category: TransactionCategory?): Int {
        return runCatching { category?.colorHex?.let(Color::parseColor) }.getOrNull()
            ?: resourceManger.getColor(CoreR.color.colorNoCategory)
    }

}