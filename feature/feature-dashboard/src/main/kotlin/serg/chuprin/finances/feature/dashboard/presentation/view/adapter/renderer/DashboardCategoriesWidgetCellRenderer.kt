package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.renderer

import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.android.synthetic.main.cell_widget_dashboard_categories.*
import serg.chuprin.adapter.*
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.core.piechart.model.PieChartData
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardCategoryChipCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell

/**
 * Created by Sergey Chuprin on 24.04.2020.
 */
class DashboardCategoriesWidgetCellRenderer : ContainerRenderer<DashboardWidgetCell.Categories>() {

    override val type: Int = R.layout.cell_widget_dashboard_categories

    private val categoryCellsAdapter =
        DiffMultiViewAdapter<DashboardCategoryChipCell>(DiffCallback()).apply {
            registerRenderer(DashboardCategoryChipCellRenderer())
        }

    override fun bindView(holder: ContainerHolder, model: DashboardWidgetCell.Categories) {
        categoryCellsAdapter.setItems(model.categoryCells)
        holder.pieChart.setData(
            animate = true,
            // TODO
            secondaryText = "Expenses",
            primaryText = model.totalAmount,
            pieChartData = PieChartData(model.chartParts)
        )
    }

    override fun onVhCreated(
        holder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(holder.recyclerView) {
            adapter = categoryCellsAdapter
            layoutManager = FlexboxLayoutManager(context).apply {
                justifyContent = JustifyContent.FLEX_START
            }
        }
    }

}