package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.transactions

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.cells.TransactionCell
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.DividerAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback

/**
 * Created by Sergey Chuprin on 23.04.2020.
 */
class DashboardRecentTransactionsAdapter :
    DiffMultiViewAdapter<BaseCell>(DiffCallback()),
    DividerAdapter {

    override fun shouldDrawDividerForCellAt(adapterPosition: Int): Boolean {
        return adapterPosition != itemCount && getItemOrNull(adapterPosition) is TransactionCell
    }

}