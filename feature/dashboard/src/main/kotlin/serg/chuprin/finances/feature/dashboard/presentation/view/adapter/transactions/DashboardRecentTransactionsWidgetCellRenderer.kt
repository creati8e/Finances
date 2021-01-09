package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.transactions

import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.cell_widget_dashboard_recent_transactions.*
import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.ContainerRenderer
import serg.chuprin.adapter.LongClick
import serg.chuprin.finances.core.api.presentation.model.cells.TransactionCell
import serg.chuprin.finances.core.api.presentation.view.adapter.decoration.CellDividerDecoration
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.TransactionCellRenderer
import serg.chuprin.finances.core.api.presentation.view.extensions.makeVisibleOrGone
import serg.chuprin.finances.core.api.presentation.view.extensions.onClick
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.transactions.DashboardRecentTransactionsZeroDataCell

/**
 * Created by Sergey Chuprin on 28.05.2020.
 */
class DashboardRecentTransactionsWidgetCellRenderer(
    private val onTransactionClick: (TransactionCell) -> Unit,
    private val clickOnShowMoreTransactions: () -> Unit
) : ContainerRenderer<DashboardWidgetCell.RecentTransactions>() {

    override val type: Int = R.layout.cell_widget_dashboard_recent_transactions

    private val recentTransactionsAdapter = DashboardRecentTransactionsAdapter().apply {
        registerRenderer<DashboardRecentTransactionsZeroDataCell>(
            R.layout.cell_dashboard_recent_transactions_zero_data
        )
        registerRenderer(TransactionCellRenderer())
        clickListener = { cell, _, _ ->
            if (cell is TransactionCell) {
                onTransactionClick(cell)
            }
        }
    }

    override fun bindView(viewHolder: ContainerHolder, cell: DashboardWidgetCell.RecentTransactions) {
        recentTransactionsAdapter.setItems(cell.cells)
        with(viewHolder) {
            showMoreButton.makeVisibleOrGone(cell.showMoreTransactionsButtonIsVisible)
        }
    }

    override fun onVhCreated(
        viewHolder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(viewHolder) {
            showMoreButton.onClick(clickOnShowMoreTransactions)
            with(recentTransactionsRecyclerView) {
                adapter = recentTransactionsAdapter
                layoutManager = LinearLayoutManager(context)
                isNestedScrollingEnabled = false
                addItemDecoration(
                    CellDividerDecoration(
                        context = context,
                        marginEndDp = -8,
                        marginStartDp = 36,
                        dividerAdapter = recentTransactionsAdapter
                    )
                )
            }
        }
    }

}