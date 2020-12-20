package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.categories

import android.os.Parcelable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_widget_dashboard_categories.*
import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.adapter.LongClick
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.extensions.onScrollStateChanged
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories.DashboardCategoryChipCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories.page.DashboardExpenseCategoriesPageCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories.page.DashboardIncomeCategoriesPageCell
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.categories.diff.DashboardCategoriesWidgetChangedPayload
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.categories.diff.DashboardCategoryPagesDiffCallback

/**
 * Created by Sergey Chuprin on 28.05.2020.
 */
class DashboardCategoriesWidgetCellRenderer(
    private val onCategoryClicked: (cell: DashboardCategoryChipCell) -> Unit
) : ContainerRenderer<DashboardWidgetCell.Categories>() {

    override val type: Int = R.layout.cell_widget_dashboard_categories

    private var adapterState: Parcelable? = null

    private val pageCellsAdapter =
        DiffMultiViewAdapter(DashboardCategoryPagesDiffCallback()).apply {
            registerRenderer(
                DashboardCategoryPageCellRenderer(
                    R.layout.cell_dashboard_income_categories_page,
                    onCategoryClicked
                ), DashboardIncomeCategoriesPageCell::class.java
            )
            registerRenderer(
                DashboardCategoryPageCellRenderer(
                    R.layout.cell_dashboard_expense_categories_page,
                    onCategoryClicked
                ),
                DashboardExpenseCategoriesPageCell::class.java
            )
        }

    override fun bindView(holder: ContainerHolder, model: DashboardWidgetCell.Categories) {
        setCells(holder, model)
    }

    override fun bindView(
        holder: ContainerHolder,
        model: DashboardWidgetCell.Categories,
        payloads: MutableList<Any>
    ) {
        if (DashboardCategoriesWidgetChangedPayload in payloads) {
            setCells(holder, model)
        }
    }

    private fun setCells(
        holder: ContainerHolder,
        model: DashboardWidgetCell.Categories
    ) {
        pageCellsAdapter.setItems(model.pageCells) {
            if (adapterState != null) {
                holder.categoryPagesRecyclerView.layoutManager?.onRestoreInstanceState(adapterState)
            }
        }
    }

    override fun onVhCreated(
        holder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(holder) {
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

}