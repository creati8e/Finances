package serg.chuprin.finances.feature.transactions.presentation.view.adapter.renderer

import kotlinx.android.synthetic.main.cell_data_period_summary.*
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.finances.core.api.extensions.containsType
import serg.chuprin.finances.feature.transactions.R
import serg.chuprin.finances.feature.transactions.presentation.model.cells.TransactionReportDataPeriodSummaryCell

/**
 * Created by Sergey Chuprin on 22.12.2020.
 */
class TransactionReportDataPeriodSummaryCellRenderer :
    ContainerRenderer<TransactionReportDataPeriodSummaryCell>() {

    override val type: Int = R.layout.cell_data_period_summary

    override fun bindView(holder: ContainerHolder, model: TransactionReportDataPeriodSummaryCell) {
        bind(holder, model)
    }

    override fun bindView(
        holder: ContainerHolder,
        model: TransactionReportDataPeriodSummaryCell,
        payloads: MutableList<Any>
    ) {
        if (payloads.containsType<TransactionReportDataPeriodSummaryCell.ChangedPayload>()) {
            bind(holder, model)
        }
    }

    private fun bind(
        holder: ContainerHolder,
        model: TransactionReportDataPeriodSummaryCell
    ) {
        holder.titleTextView.text = model.title
        holder.valueTextView.text = model.value
    }

}