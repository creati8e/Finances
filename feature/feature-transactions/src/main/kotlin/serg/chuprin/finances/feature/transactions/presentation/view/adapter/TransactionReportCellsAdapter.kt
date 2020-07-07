package serg.chuprin.finances.feature.transactions.presentation.view.adapter

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.cells.TransactionCell
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.DividerAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.DateDividerCellRenderer
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.TransactionCellRenderer
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.ZeroDataCellRenderer

/**
 * Created by Sergey Chuprin on 07.07.2020.
 */
class TransactionReportCellsAdapter : DiffMultiViewAdapter<BaseCell>(DiffCallback()),
    DividerAdapter {

    init {
        registerRenderer(ZeroDataCellRenderer())
        registerRenderer(TransactionCellRenderer())
        registerRenderer(DateDividerCellRenderer())
    }

    override fun shouldDrawDividerForCellAt(adapterPosition: Int): Boolean {
        return getItemOrNull(adapterPosition) is TransactionCell
    }

}