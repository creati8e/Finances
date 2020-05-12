package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.dsl.categories

import android.content.res.ColorStateList
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearSnapHelper
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.android.synthetic.main.cell_dashboard_category.*
import kotlinx.android.synthetic.main.cell_widget_dashboard_categories.*
import kotlinx.android.synthetic.main.view_dashboard_categories_page.*
import serg.chuprin.finances.core.api.presentation.view.adapter.dsl.context.RecyclerViewAdapterContext
import serg.chuprin.finances.core.api.presentation.view.adapter.dsl.context.RecyclerViewNestedAdapterContext
import serg.chuprin.finances.core.piechart.model.PieChartData
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories.DashboardCategoryChipCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories.page.DashboardCategoriesPageCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories.page.DashboardCategoriesPageZeroDataCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories.page.DashboardExpenseCategoriesPageCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories.page.DashboardIncomeCategoriesPageCell
import serg.chuprin.finances.feature.dashboard.presentation.model.store.DashboardIntent
import serg.chuprin.finances.feature.dashboard.presentation.model.viewmodel.DashboardViewModel
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.dsl.categories.diff.DashboardCategoryChipCellsDiffCallback
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.dsl.categories.diff.DashboardCategoryPagesDiffCallback

/**
 * Created by Sergey Chuprin on 29.04.2020.
 */
fun RecyclerViewAdapterContext.setupCategoriesWidgetBinding(viewModel: DashboardViewModel) {
    add<DashboardWidgetCell.Categories>(R.layout.cell_widget_dashboard_categories) {

        bind { cell, payloads ->
            if (payloads.isEmpty() || DashboardCategoriesWidgetChangedPayload in payloads) {
                setNestedCells(cell.pageCells)
            }
        }

        // Horizontal list of pages with chart and categories.
        nestedHorizontalList(
            { categoryPagesRecyclerView },
            DashboardCategoryPagesDiffCallback()
        ) {
            setupRecyclerView {
                LinearSnapHelper().attachToRecyclerView(categoryPagesRecyclerView)
                pageIndicator.attachToRecyclerView(categoryPagesRecyclerView)
            }
            bindPage<DashboardIncomeCategoriesPageCell>(
                R.layout.cell_dashboard_income_categories_page,
                viewModel
            )
            bindPage<DashboardExpenseCategoriesPageCell>(
                R.layout.cell_dashboard_expense_categories_page,
                viewModel
            )
        }
    }
}

private inline fun <reified T : DashboardCategoriesPageCell> RecyclerViewNestedAdapterContext.bindPage(
    @LayoutRes pageLayoutRes: Int,
    viewModel: DashboardViewModel
) {
    add<T>(pageLayoutRes) {

        bind { cell, _ ->
            setNestedCells(cell.categoryCells)
            pieChart.setData(
                animate = false,
                secondaryText = cell.label,
                primaryText = cell.totalAmount,
                pieChartData = PieChartData(cell.chartParts)
            )
        }

        // List with categories.
        nestedList(
            { context ->
                FlexboxLayoutManager(context).apply {
                    justifyContent = JustifyContent.CENTER
                }
            },
            { categoryChipsRecyclerView },
            DashboardCategoryChipCellsDiffCallback()
        ) {
            add<DashboardCategoriesPageZeroDataCell>(
                R.layout.cell_dashboard_categories_page_zero_data
            )
            add<DashboardCategoryChipCell>(R.layout.cell_dashboard_category) {
                bind { cell, payloads ->
                    if (payloads.isEmpty() || DashboardCategoryChipCellChangedPayload in payloads) {
                        with(categoryChip) {
                            text = cell.chipText
                            tag = cell.transitionName
                            transitionName = cell.transitionName
                            chipBackgroundColor = ColorStateList.valueOf(cell.colorInt)
                        }
                    }
                }

                setupViews {
                    setClickListener(
                        categoryChip,
                        { cell ->
                            viewModel.dispatchIntent(DashboardIntent.ClickOnCategory(cell))
                        }
                    )
                }
            }
        }
    }
}

/**
 * Represents particular category change event.
 */
object DashboardCategoryChipCellChangedPayload

/**
 * Represents the whole widget change event.
 */
object DashboardCategoriesWidgetChangedPayload

/**
 * Represents particular widget page change event.
 */
object DashboardCategoriesPageChangedPayload