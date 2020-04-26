package serg.chuprin.finances.feature.dashboard.presentation.model.builder

import android.graphics.Color
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
    private val amountFormatter: AmountFormatter,
    private val resourceManger: ResourceManger
) : DashboardWidgetCellBuilder {

    // TODO: Add colors to category entities.
    private val colors = listOf(
        Color.parseColor("#FFC107"),
        Color.parseColor("#FF03A9F4"),
        Color.parseColor("#FF8BC34A"),
        Color.parseColor("#9ccc65")
    )

    override fun build(widget: DashboardWidget): DashboardWidgetCell? {
        if (widget !is DashboardWidget.Categories) {
            return null
        }

        val chartParts = widget.categoryAmounts.entries.mapIndexed { index, (_, amount) ->
            PieChartDataPart(
                colorInt = colors[index],
                value = amount.abs().toFloat()
            )
        }

        val categoryCells = widget.categoryAmounts.entries.mapIndexed { index, (category, amount) ->
            val categoryName = category?.name ?: resourceManger.getString(CoreR.string.no_category)
            val chipText = "$categoryName ${amountFormatter.format(amount.abs(), widget.currency)}"
            DashboardCategoryChipCell(colors.getOrNull(index) ?: Color.GRAY, chipText, category)
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

}