package serg.chuprin.finances.feature.transactions.presentation.view.adapter.renderer

import kotlinx.android.synthetic.main.cell_data_period_summary.*
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.finances.core.api.extensions.containsType
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.ContainerRenderer
import serg.chuprin.finances.feature.transactions.R
import serg.chuprin.finances.feature.transactions.presentation.model.cells.TransactionReportDataPeriodSummaryCell

/**
 * Created by Sergey Chuprin on 22.12.2020.
 */
class TransactionReportDataPeriodSummaryCellRenderer :
    ContainerRenderer<TransactionReportDataPeriodSummaryCell>() {

    override val type: Int = R.layout.cell_data_period_summary

    override fun bindView(
        viewHolder: ContainerHolder,
        cell: TransactionReportDataPeriodSummaryCell
    ) {
        bind(viewHolder, cell)
    }

    override fun bindView(
        viewHolder: ContainerHolder,
        cell: TransactionReportDataPeriodSummaryCell,
        payloads: MutableList<Any>
    ) {
        if (payloads.containsType<TransactionReportDataPeriodSummaryCell.ChangedPayload>()) {
            bind(viewHolder, cell)
        }
    }

    private fun bind(
        viewHolder: ContainerHolder,
        cell: TransactionReportDataPeriodSummaryCell
    ) {
        viewHolder.titleTextView.text = cell.title
        viewHolder.valueTextView.text = cell.value
    }

}