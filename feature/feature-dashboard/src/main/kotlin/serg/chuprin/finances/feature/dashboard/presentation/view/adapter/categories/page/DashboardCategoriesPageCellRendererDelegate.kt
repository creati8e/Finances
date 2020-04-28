package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.categories.page

import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.android.synthetic.main.view_dashboard_categories_page.*
import serg.chuprin.adapter.*
import serg.chuprin.finances.core.piechart.model.PieChartData
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories.page.DashboardCategoriesPageCell
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.categories.DashboardCategoriesPageZeroDataCellRenderer
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.categories.DashboardCategoryChipCellRenderer
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.categories.diff.DashboardCategoryChipCellsDiffCallback
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.categories.diff.payload.DashboardCategoriesPageChangedPayload

/**
 * Created by Sergey Chuprin on 28.04.2020.
 */
abstract class DashboardCategoriesPageCellRendererDelegate<T : DashboardCategoriesPageCell>(
    override val type: Int
) : ContainerRenderer<T>() {

    private val categoryCellsAdapter = DiffMultiViewAdapter(
        DashboardCategoryChipCellsDiffCallback()
    ).apply {
        registerRenderer(DashboardCategoryChipCellRenderer())
        registerRenderer(DashboardCategoriesPageZeroDataCellRenderer())
    }

    override fun bindView(holder: ContainerHolder, model: T) {
        showData(holder, model)
    }

    override fun bindView(holder: ContainerHolder, model: T, payloads: MutableList<Any>) {
        if (DashboardCategoriesPageChangedPayload in payloads) {
            showData(holder, model)
        }
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

    private fun showData(holder: ContainerHolder, model: T) {
        categoryCellsAdapter.setItems(model.categoryCells)
        holder.pieChart.setData(
            animate = false,
            secondaryText = model.label,
            primaryText = model.totalAmount,
            pieChartData = PieChartData(model.chartParts)
        )
    }

}