package serg.chuprin.finances.feature.transactions.presentation.view.adapter.renderer

import kotlinx.android.synthetic.main.cell_transaction_report_data_period_amount_chart.*
import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.ContainerRenderer
import serg.chuprin.adapter.LongClick
import serg.chuprin.finances.core.api.extensions.containsType
import serg.chuprin.finances.core.api.presentation.view.extensions.onViewClick
import serg.chuprin.finances.feature.transactions.R
import serg.chuprin.finances.feature.transactions.presentation.model.cells.TransactionReportDataPeriodAmountChartCell

/**
 * Created by Sergey Chuprin on 21.12.2020.
 */
class TransactionDataPeriodAmountChartCellRenderer :
    ContainerRenderer<TransactionReportDataPeriodAmountChartCell>() {

    override val type: Int = R.layout.cell_transaction_report_data_period_amount_chart

    override fun bindView(
        viewHolder: ContainerHolder,
        cell: TransactionReportDataPeriodAmountChartCell
    ) {
        bind(viewHolder, cell)
    }

    override fun bindView(
        viewHolder: ContainerHolder,
        cell: TransactionReportDataPeriodAmountChartCell,
        payloads: MutableList<Any>
    ) {
        if (payloads.containsType<TransactionReportDataPeriodAmountChartCell.ChangedPayload>()) {
            bind(viewHolder, cell)
        }
    }

    override fun onVhCreated(
        viewHolder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(viewHolder) {
            itemView.onViewClick { view ->
                clickListener?.onClick(view, adapterPosition)
            }
        }
    }

    private fun bind(
        viewHolder: ContainerHolder,
        cell: TransactionReportDataPeriodAmountChartCell
    ) {
        with(viewHolder) {
            chartBar.setProgress(cell.barFill)
            itemView.isActivated = cell.isChosen
            dateTextView.text = cell.formattedPeriodName
        }
    }

}