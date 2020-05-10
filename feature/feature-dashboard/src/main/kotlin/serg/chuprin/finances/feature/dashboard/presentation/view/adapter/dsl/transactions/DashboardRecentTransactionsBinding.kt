package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.dsl.transactions

import android.content.res.ColorStateList
import kotlinx.android.synthetic.main.cell_dashboard_transaction.*
import kotlinx.android.synthetic.main.cell_widget_dashboard_recent_transactions.*
import serg.chuprin.finances.core.api.presentation.view.adapter.decoration.CellDividerDecoration
import serg.chuprin.finances.core.api.presentation.view.adapter.dsl.context.RecyclerViewAdapterContext
import serg.chuprin.finances.core.api.presentation.view.extensions.makeVisibleOrGone
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.transactions.DashboardRecentTransactionsZeroDataCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.transactions.DashboardTransactionCell

/**
 * Created by Sergey Chuprin on 29.04.2020.
 */
fun RecyclerViewAdapterContext.setupRecentTransactionsBinding() {
    add<DashboardWidgetCell.RecentTransactions>(R.layout.cell_widget_dashboard_recent_transactions) {

        bind { cell, _ ->
            setNestedCells(cell.cells)
            showMoreButton.makeVisibleOrGone(cell.showMoreTransactionsButtonIsVisible)
        }

        val recentTransactionsAdapter = DashboardRecentTransactionsAdapter()
        // Non-scrollable vertical list with transactions.
        nestedVerticalList(recentTransactionsAdapter, {
            recentTransactionsRecyclerView.apply {
                isNestedScrollingEnabled = false
                addItemDecoration(
                    CellDividerDecoration(
                        context = context,
                        marginEndDp = 8,
                        marginStartDp = 8,
                        dividerAdapter = recentTransactionsAdapter
                    )
                )
            }
        }) {
            add<DashboardRecentTransactionsZeroDataCell>(
                R.layout.cell_dashboard_recent_transactions_zero_data
            )
            add<DashboardTransactionCell>(R.layout.cell_dashboard_transaction) {
                // TODO: Maybe reuse.
                bind { cell, _ ->
                    amountTextView.text = cell.amount
                    dateTextView.text = cell.formattedDate
                    amountTextView.isActivated = cell.isIncome
                    subcategoryTextView.text = cell.subcategoryName
                    parentCategoryTextView.text = cell.parentCategoryName
                    transactionColorDot.imageTintList = ColorStateList.valueOf(cell.color)
                    subcategoryTextView.makeVisibleOrGone(cell.subcategoryName.isNotEmpty())
                }
            }
        }
    }
}