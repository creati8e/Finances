package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.categories

import android.content.res.ColorStateList
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.cell_dashboard_category.*
import kotlinx.android.synthetic.main.view_dashboard_categories_page.*
import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.adapter.LongClick
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.extensions.onClick
import serg.chuprin.finances.core.piechart.model.PieChartData
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories.DashboardCategoryChipCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories.page.DashboardCategoriesPageCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories.page.DashboardCategoriesPageZeroDataCell
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.categories.diff.DashboardCategoriesPageChangedPayload
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.categories.diff.DashboardCategoryChipCellChangedPayload
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.categories.diff.DashboardCategoryChipCellsDiffCallback

/**
 * Created by Sergey Chuprin on 28.05.2020.
 */
class DashboardCategoryPageCellRenderer(
    override val type: Int,
    private val onCategoryClicked: (cell: DashboardCategoryChipCell) -> Unit
) : ContainerRenderer<DashboardCategoriesPageCell>() {

    private class DashboardCategoryChipCellRenderer(
        private val onCategoryClicked: (adapterPosition: Int) -> Unit
    ) : ContainerRenderer<DashboardCategoryChipCell>() {

        override val type: Int = R.layout.cell_dashboard_category

        override fun bindView(holder: ContainerHolder, model: DashboardCategoryChipCell) {
            bindData(holder.categoryChip, model)
        }

        override fun bindView(
            holder: ContainerHolder,
            model: DashboardCategoryChipCell,
            payloads: MutableList<Any>
        ) {
            if (DashboardCategoryChipCellChangedPayload in payloads) {
                bindData(holder.categoryChip, model)
            }
        }

        override fun onVhCreated(
            holder: ContainerHolder,
            clickListener: Click?,
            longClickListener: LongClick?
        ) {
            with(holder) {
                categoryChip.onClick {
                    onCategoryClicked(adapterPosition)
                }
            }
        }

        private fun bindData(chip: Chip, cell: DashboardCategoryChipCell) {
            with(chip) {
                text = cell.chipText
                tag = cell.transitionName
                transitionName = cell.transitionName
                chipBackgroundColor = ColorStateList.valueOf(cell.colorInt)
            }
        }

    }

    private val categoryCellsAdapter = DiffMultiViewAdapter(
        DashboardCategoryChipCellsDiffCallback()
    ).apply {
        registerRenderer<DashboardCategoriesPageZeroDataCell>(
            R.layout.cell_dashboard_categories_page_zero_data
        )
        registerRenderer(DashboardCategoryChipCellRenderer(::handleOnCategoryChipClick))
    }

    override fun bindView(holder: ContainerHolder, model: DashboardCategoriesPageCell) {
        bindData(holder, model)
    }

    override fun bindView(
        holder: ContainerHolder,
        model: DashboardCategoriesPageCell,
        payloads: MutableList<Any>
    ) {
        if (DashboardCategoriesPageChangedPayload in payloads) {
            bindData(holder, model)
        }
    }

    override fun onVhCreated(
        holder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(holder.categoryChipsRecyclerView) {
            adapter = categoryCellsAdapter
            layoutManager = com.google.android.flexbox.FlexboxLayoutManager(context).apply {
                justifyContent = com.google.android.flexbox.JustifyContent.CENTER
            }
        }
    }

    private fun bindData(
        holder: ContainerHolder,
        model: DashboardCategoriesPageCell
    ) {
        categoryCellsAdapter.setItems(model.categoryCells)
        holder.pieChart.setData(
            animate = false,
            secondaryText = model.label,
            primaryText = model.totalAmount,
            pieChartData = PieChartData(model.chartParts)
        )
    }

    private fun handleOnCategoryChipClick(adapterPosition: Int) {
        val itemOrNull = categoryCellsAdapter.getItemOrNull(adapterPosition)
        val cell = itemOrNull as? DashboardCategoryChipCell ?: return
        onCategoryClicked(cell)
    }

}