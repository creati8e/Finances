package serg.chuprin.finances.feature.transactions.presentation.view.adapter

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.cells.TransactionCell
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.DividerAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.DateDividerCellRenderer
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.TransactionCellRenderer
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.ZeroDataCellRenderer
import serg.chuprin.finances.feature.transactions.presentation.model.cells.TransactionReportDataPeriodAmountChartCell
import serg.chuprin.finances.feature.transactions.presentation.view.adapter.diff.TransactionReportCellsDiffCallback
import serg.chuprin.finances.feature.transactions.presentation.view.adapter.renderer.TransactionReportChartListCellRenderer
import serg.chuprin.finances.feature.transactions.presentation.view.adapter.renderer.TransactionReportDataPeriodSummaryCellRenderer

/**
 * Created by Sergey Chuprin on 07.07.2020.
 */
class TransactionReportCellsAdapter(
    onChartCellClicked: (TransactionReportDataPeriodAmountChartCell) -> Unit
) : DiffMultiViewAdapter<BaseCell>(TransactionReportCellsDiffCallback()),
    DividerAdapter {

    init {
        registerRenderer(ZeroDataCellRenderer())
        registerRenderer(TransactionCellRenderer())
        registerRenderer(DateDividerCellRenderer())
        registerRenderer(TransactionReportDataPeriodSummaryCellRenderer())
        registerRenderer(TransactionReportChartListCellRenderer(onChartCellClicked))
    }

    override fun shouldDrawDividerForCellAt(adapterPosition: Int): Boolean {
        return getItemOrNull(adapterPosition) is TransactionCell
    }

}