package serg.chuprin.finances.feature.transactions.presentation.view.adapter.renderer

import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.cell_transaction_report_chart_list.*
import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.adapter.LongClick
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.feature.transactions.R
import serg.chuprin.finances.feature.transactions.presentation.model.cells.TransactionReportChartCell
import serg.chuprin.finances.feature.transactions.presentation.model.cells.TransactionReportChartListCell

/**
 * Created by Sergey Chuprin on 22.12.2020.
 */
class TransactionReportChartListCellRenderer(
    private val onChartCellClicked: (TransactionReportChartCell) -> Unit
) : ContainerRenderer<TransactionReportChartListCell>() {

    override val type: Int = R.layout.cell_transaction_report_chart_list

    private val chartCellsAdapter = DiffMultiViewAdapter(
        DiffCallback<TransactionReportChartCell>()
    ).apply {
        registerRenderer(TransactionChartCellRenderer())
    }

    override fun bindView(holder: ContainerHolder, model: TransactionReportChartListCell) {
        chartCellsAdapter.setItems(model.chartCells)
    }

    override fun onVhCreated(
        holder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(holder) {
            with(recyclerView) {
                adapter = chartCellsAdapter
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false).apply {
                        stackFromEnd = true
                    }
            }
            chartCellsAdapter.clickListener = { chartCell, _, _ ->
                onChartCellClicked(chartCell)
            }
        }
    }

}