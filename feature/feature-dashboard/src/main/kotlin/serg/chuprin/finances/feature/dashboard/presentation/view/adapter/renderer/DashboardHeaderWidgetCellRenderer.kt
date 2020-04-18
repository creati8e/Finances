package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.renderer

import kotlinx.android.synthetic.main.cell_dashboard_header_widget.*
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.diff.payload.DashboardHeaderWidgetChangedPayload

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
class DashboardHeaderWidgetCellRenderer :
    ContainerRenderer<DashboardWidgetCell.DashboardHeaderCell>() {

    override val type: Int = R.layout.cell_dashboard_header_widget

    override fun bindView(holder: ContainerHolder, model: DashboardWidgetCell.DashboardHeaderCell) {
        bindViews(holder, model)
    }

    override fun bindView(
        holder: ContainerHolder,
        model: DashboardWidgetCell.DashboardHeaderCell,
        payloads: MutableList<Any>
    ) {
        if (DashboardHeaderWidgetChangedPayload in payloads) {
            bindViews(holder, model)
        }
    }

    private fun bindViews(
        holder: ContainerHolder,
        model: DashboardWidgetCell.DashboardHeaderCell
    ) {
        with(holder) {
            balanceTextView.text = model.balance
            currentPeriodTextView.text = model.currentPeriod
            incomesCardView.setAmountText(model.incomesAmount)
            expensesCardView.setAmountText(model.expensesAmount)
        }
    }

}