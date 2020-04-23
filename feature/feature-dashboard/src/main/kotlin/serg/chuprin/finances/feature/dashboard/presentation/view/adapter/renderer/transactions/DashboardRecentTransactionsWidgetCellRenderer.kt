package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.renderer.transactions

import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.cell_widget_dashboard_recent_transactions.*
import serg.chuprin.adapter.*
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.core.api.presentation.view.extensions.makeVisibleOrGone
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
class DashboardRecentTransactionsWidgetCellRenderer :
    ContainerRenderer<DashboardWidgetCell.RecentTransactions>() {

    override val type: Int = R.layout.cell_widget_dashboard_recent_transactions

    private val transactionCellsAdapter = DiffMultiViewAdapter(DiffCallback<BaseCell>()).apply {
        registerRenderer(DashboardTransactionCellRenderer())
        registerRenderer(DashboardRecentTransactionsZeroDataCellRenderer())
    }

    override fun bindView(holder: ContainerHolder, model: DashboardWidgetCell.RecentTransactions) {
        transactionCellsAdapter.setItems(model.cells)
        holder.showMoreButton.makeVisibleOrGone(model.showMoreTransactionsButtonIsVisible)
    }

    override fun onVhCreated(
        holder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(holder.recyclerView) {
            adapter = transactionCellsAdapter
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context)
        }
    }

}