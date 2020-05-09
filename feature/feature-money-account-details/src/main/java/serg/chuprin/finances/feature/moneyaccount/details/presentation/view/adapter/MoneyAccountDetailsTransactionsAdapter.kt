package serg.chuprin.finances.feature.moneyaccount.details.presentation.view.adapter

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.cells.DateDividerCell
import serg.chuprin.finances.core.api.presentation.model.cells.TransactionCell
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.DividerAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.DateDividerCellRenderer
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.TransactionCellRenderer
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.ZeroDataCellRenderer

/**
 * Created by Sergey Chuprin on 09.05.2020.
 */
class MoneyAccountDetailsTransactionsAdapter :
    DiffMultiViewAdapter<BaseCell>(DiffCallback()),
    DividerAdapter {

    init {
        registerRenderer(ZeroDataCellRenderer())
        registerRenderer(TransactionCellRenderer())
        registerRenderer(DateDividerCellRenderer())
    }

    override fun shouldDrawDividerForCellAt(adapterPosition: Int): Boolean {
        val cell = getItemOrNull(adapterPosition) ?: return false
        return cell is TransactionCell && getItemOrNull(adapterPosition + 1) is DateDividerCell
    }

}