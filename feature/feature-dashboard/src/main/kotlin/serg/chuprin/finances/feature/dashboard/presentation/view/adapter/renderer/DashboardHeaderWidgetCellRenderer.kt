package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.renderer

import kotlinx.android.synthetic.main.cell_widget_dashboard_header.*
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.diff.payload.DashboardHeaderWidgetChangedPayload

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
class DashboardHeaderWidgetCellRenderer :
    ContainerRenderer<DashboardWidgetCell.Header>() {

    override val type: Int = R.layout.cell_widget_dashboard_header

    override fun bindView(holder: ContainerHolder, model: DashboardWidgetCell.Header) {
        bindViews(holder, model)
    }

    override fun bindView(
        holder: ContainerHolder,
        model: DashboardWidgetCell.Header,
        payloads: MutableList<Any>
    ) {
        if (DashboardHeaderWidgetChangedPayload in payloads) {
            bindViews(holder, model)
        }
    }

    private fun bindViews(
        holder: ContainerHolder,
        model: DashboardWidgetCell.Header
    ) {
        with(holder) {
            balanceTextView.text = model.balance
            currentPeriodTextView.text = model.currentPeriod
            incomesCardView.setAmountText(model.incomesAmount)
            expensesCardView.setAmountText(model.expensesAmount)
        }
    }

}