package serg.chuprin.finances.feature.dashboard.presentation.view.adapter

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.DividerAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.transactions.DashboardTransactionCell
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.renderer.transactions.DashboardRecentTransactionsZeroDataCellRenderer
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.renderer.transactions.DashboardTransactionCellRenderer

/**
 * Created by Sergey Chuprin on 23.04.2020.
 */
class DashboardRecentTransactionsAdapter :
    DiffMultiViewAdapter<BaseCell>(DiffCallback()),
    DividerAdapter {

    init {
        registerRenderer(DashboardTransactionCellRenderer())
        registerRenderer(DashboardRecentTransactionsZeroDataCellRenderer())
    }

    override fun shouldDrawDividerForCellAt(adapterPosition: Int): Boolean {
        return adapterPosition != itemCount
                && getItemOrNull(adapterPosition) is DashboardTransactionCell
    }

}