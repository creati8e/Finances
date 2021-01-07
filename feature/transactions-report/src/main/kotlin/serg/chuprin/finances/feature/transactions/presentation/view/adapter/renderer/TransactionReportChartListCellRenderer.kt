package serg.chuprin.finances.feature.transactions.presentation.view.adapter.renderer

import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.cell_transaction_report_chart_list.*
import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.adapter.LongClick
import serg.chuprin.finances.core.api.extensions.containsType
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.feature.transactions.R
import serg.chuprin.finances.feature.transactions.presentation.model.cells.TransactionReportChartListCell
import serg.chuprin.finances.feature.transactions.presentation.model.cells.TransactionReportDataPeriodAmountChartCell

/**
 * Created by Sergey Chuprin on 22.12.2020.
 */
class TransactionReportChartListCellRenderer(
    private val onChartCellClicked: (TransactionReportDataPeriodAmountChartCell) -> Unit
) : ContainerRenderer<TransactionReportChartListCell>() {

    override val type: Int = R.layout.cell_transaction_report_chart_list

    private val dataPeriodChartCellsAdapter = DiffMultiViewAdapter(
        DiffCallback<TransactionReportDataPeriodAmountChartCell>()
    ).apply {
        registerRenderer(TransactionDataPeriodAmountChartCellRenderer())
    }

    override fun bindView(holder: ContainerHolder, model: TransactionReportChartListCell) {
        dataPeriodChartCellsAdapter.setItems(model.dataPeriodAmountChartCells)
    }

    override fun bindView(
        holder: ContainerHolder,
        model: TransactionReportChartListCell,
        payloads: MutableList<Any>
    ) {
        if (payloads.containsType<TransactionReportChartListCell.ChangedPayload>()) {
            dataPeriodChartCellsAdapter.setItems(model.dataPeriodAmountChartCells)
        }
    }

    override fun onVhCreated(
        holder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(holder) {
            with(recyclerView) {
                adapter = dataPeriodChartCellsAdapter
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false).apply {
                        stackFromEnd = true
                    }
            }
            dataPeriodChartCellsAdapter.clickListener = { chartCell, _, _ ->
                onChartCellClicked(chartCell)
            }
        }
    }

}