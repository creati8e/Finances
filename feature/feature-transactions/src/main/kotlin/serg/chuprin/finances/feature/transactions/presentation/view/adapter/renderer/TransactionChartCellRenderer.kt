package serg.chuprin.finances.feature.transactions.presentation.view.adapter.renderer

import kotlinx.android.synthetic.main.cell_transaction_report_chart.*
import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.adapter.LongClick
import serg.chuprin.finances.core.api.presentation.view.extensions.onViewClick
import serg.chuprin.finances.feature.transactions.R
import serg.chuprin.finances.feature.transactions.presentation.model.cells.TransactionReportChartCell

/**
 * Created by Sergey Chuprin on 21.12.2020.
 */
class TransactionChartCellRenderer : ContainerRenderer<TransactionReportChartCell>() {

    override val type: Int = R.layout.cell_transaction_report_chart

    override fun bindView(holder: ContainerHolder, model: TransactionReportChartCell) {
        with(holder) {
            chartBar.setProgress(model.barFill)
            dateTextView.text = model.formattedPeriodName
        }
    }

    override fun onVhCreated(
        holder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(holder) {
            itemView.onViewClick { view ->
                clickListener?.onClick(view, adapterPosition)
            }
        }
    }

}