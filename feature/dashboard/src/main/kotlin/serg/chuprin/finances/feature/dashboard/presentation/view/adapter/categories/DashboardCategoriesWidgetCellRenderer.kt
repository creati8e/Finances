package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.categories

import android.os.Parcelable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_widget_dashboard_categories.*
import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.ContainerRenderer
import serg.chuprin.adapter.LongClick
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.extensions.onScrollStateChanged
import serg.chuprin.finances.core.categories.shares.presentation.view.adapter.renderer.CategorySharesCellRenderer
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories.DashboardCategoryShareCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories.page.DashboardCategoriesPageZeroDataCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories.page.DashboardExpenseCategoriesPageCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories.page.DashboardIncomeCategoriesPageCell
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.categories.diff.DashboardCategoriesWidgetChangedPayload
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.categories.diff.DashboardCategoryPagesDiffCallback

/**
 * Created by Sergey Chuprin on 28.05.2020.
 */
class DashboardCategoriesWidgetCellRenderer(
    private val onCategoryClicked: (cell: DashboardCategoryShareCell) -> Unit
) : ContainerRenderer<DashboardWidgetCell.Categories>() {

    override val type: Int = R.layout.cell_widget_dashboard_categories

    private var adapterState: Parcelable? = null

    private val pageCellsAdapter =
        DiffMultiViewAdapter(DashboardCategoryPagesDiffCallback()).apply {
            registerRenderer(
                createCategoryChipCellRenderer(R.layout.cell_dashboard_income_categories_page),
                DashboardIncomeCategoriesPageCell::class.java
            )
            registerRenderer(
                createCategoryChipCellRenderer(R.layout.cell_dashboard_expense_categories_page),
                DashboardExpenseCategoriesPageCell::class.java
            )
        }

    override fun bindView(viewHolder: ContainerHolder, cell: DashboardWidgetCell.Categories) {
        setCells(viewHolder, cell)
    }

    override fun bindView(
        viewHolder: ContainerHolder,
        cell: DashboardWidgetCell.Categories,
        payloads: MutableList<Any>
    ) {
        if (DashboardCategoriesWidgetChangedPayload in payloads) {
            setCells(viewHolder, cell)
        }
    }

    override fun onVhCreated(
        viewHolder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(viewHolder) {
            with(categoryPagesRecyclerView) {
                adapter = pageCellsAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                LinearSnapHelper().attachToRecyclerView(this)
                onScrollStateChanged { recyclerView, newState ->
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        val lm = recyclerView.layoutManager!!
                        adapterState = lm.onSaveInstanceState()
                    }
                }
            }
            pageIndicator.attachToRecyclerView(categoryPagesRecyclerView)
        }
    }

    private fun setCells(
        viewHolder: ContainerHolder,
        cell: DashboardWidgetCell.Categories
    ) {
        pageCellsAdapter.setItems(cell.pageCells) {
            if (adapterState != null) {
                viewHolder.categoryPagesRecyclerView.layoutManager?.onRestoreInstanceState(adapterState)
            }
        }
    }

    private fun createCategoryChipCellRenderer(
        layoutRes: Int
    ): CategorySharesCellRenderer<DashboardCategoryShareCell> {
        return CategorySharesCellRenderer(
            type = layoutRes,
            onCategoryClicked = onCategoryClicked,
            categoryChipCellClass = DashboardCategoryShareCell::class.java,
            categoryChipsAdapterSetup = {
                registerRenderer<DashboardCategoriesPageZeroDataCell>(
                    R.layout.cell_dashboard_categories_page_zero_data
                )
            }
        )
    }

}