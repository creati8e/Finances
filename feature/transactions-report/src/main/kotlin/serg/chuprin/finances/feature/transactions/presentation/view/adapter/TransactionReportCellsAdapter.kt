package serg.chuprin.finances.feature.transactions.presentation.view.adapter

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.cells.TransactionCell
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.DividerAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.DateDividerCellRenderer
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.SpaceCellRenderer
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.TransactionCellRenderer
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.ZeroDataCellRenderer
import serg.chuprin.finances.core.categories.shares.presentation.view.adapter.renderer.CategorySharesCellRenderer
import serg.chuprin.finances.feature.transactions.R
import serg.chuprin.finances.feature.transactions.presentation.model.cells.TransactionReportCategoryShareCell
import serg.chuprin.finances.feature.transactions.presentation.model.cells.TransactionReportCategorySharesCell
import serg.chuprin.finances.feature.transactions.presentation.model.cells.TransactionReportDataPeriodAmountChartCell
import serg.chuprin.finances.feature.transactions.presentation.view.adapter.diff.TransactionReportCellsDiffCallback
import serg.chuprin.finances.feature.transactions.presentation.view.adapter.renderer.TransactionReportChartListCellRenderer
import serg.chuprin.finances.feature.transactions.presentation.view.adapter.renderer.TransactionReportDataPeriodSummaryCellRenderer

/**
 * Created by Sergey Chuprin on 07.07.2020.
 */
class TransactionReportCellsAdapter(
    onTransactionClicked: (TransactionCell) -> Unit,
    onChartCellClicked: (TransactionReportDataPeriodAmountChartCell) -> Unit
) : DiffMultiViewAdapter<BaseCell>(TransactionReportCellsDiffCallback()),
    DividerAdapter {

    init {
        registerRenderer(SpaceCellRenderer())
        registerRenderer(ZeroDataCellRenderer())
        registerRenderer(TransactionCellRenderer())
        registerRenderer(DateDividerCellRenderer())
        registerRenderer(TransactionReportDataPeriodSummaryCellRenderer())
        registerRenderer(TransactionReportChartListCellRenderer(onChartCellClicked))

        registerRenderer(
            // TODO: Handle clicks.
            CategorySharesCellRenderer(
                onCategoryClicked = {},
                categoryChipsAdapterSetup = {},
                type = R.layout.cell_transaction_report_category_shares,
                categoryChipCellClass = TransactionReportCategoryShareCell::class.java
            ),
            TransactionReportCategorySharesCell::class.java
        )

        clickListener = { cell, _, _ ->
            if (cell is TransactionCell) {
                onTransactionClicked(cell)
            }
        }

    }

    override fun shouldDrawDividerForCellAt(adapterPosition: Int): Boolean {
        return getItemOrNull(adapterPosition) is TransactionCell
    }

}