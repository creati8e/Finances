package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.renderer

import android.graphics.Color
import kotlinx.android.synthetic.main.cell_widget_dashboard_categories.*
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.finances.core.piechart.model.PieChartData
import serg.chuprin.finances.core.piechart.model.PieChartDataPart
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell

/**
 * Created by Sergey Chuprin on 24.04.2020.
 */
class DashboardCategoriesWidgetCellRenderer : ContainerRenderer<DashboardWidgetCell.Categories>() {

    override val type: Int = R.layout.cell_widget_dashboard_categories

    private val colors = listOf(
        Color.parseColor("#FFC107"),
        Color.parseColor("#FF03A9F4"),
        Color.parseColor("#FF8BC34A"),
        Color.parseColor("#9ccc65")
    )

    override fun bindView(holder: ContainerHolder, model: DashboardWidgetCell.Categories) {
        val parts = model.widget.categoryAmounts.entries.mapIndexed { index, (_, amount) ->
            PieChartDataPart(
                amount.abs().toFloat(),
                colors[index]
            )
        }
        holder.pieChart.setData(
            animate = true,
            // TODO
            secondaryText = "Expenses",
            primaryText = model.totalAmount,
            pieChartData = PieChartData(parts)
        )
    }


}