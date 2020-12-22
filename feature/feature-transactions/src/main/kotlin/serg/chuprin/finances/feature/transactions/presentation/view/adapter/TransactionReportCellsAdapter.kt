package serg.chuprin.finances.feature.transactions.presentation.view.adapter

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.cells.TransactionCell
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.DividerAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.DateDividerCellRenderer
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.TransactionCellRenderer
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.ZeroDataCellRenderer
import serg.chuprin.finances.feature.transactions.presentation.model.cells.TransactionReportChartCell
import serg.chuprin.finances.feature.transactions.presentation.view.adapter.renderer.TransactionReportChartListCellRenderer

/**
 * Created by Sergey Chuprin on 07.07.2020.
 */
class TransactionReportCellsAdapter(
    onChartCellClicked: (TransactionReportChartCell) -> Unit
) : DiffMultiViewAdapter<BaseCell>(DiffCallback()),
    DividerAdapter {

    init {
        registerRenderer(ZeroDataCellRenderer())
        registerRenderer(TransactionCellRenderer())
        registerRenderer(DateDividerCellRenderer())
        registerRenderer(TransactionReportChartListCellRenderer(onChartCellClicked))
    }

    override fun shouldDrawDividerForCellAt(adapterPosition: Int): Boolean {
        return getItemOrNull(adapterPosition) is TransactionCell
    }

}