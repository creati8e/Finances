package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.renderer

import kotlinx.android.synthetic.main.cell_dashboard_header.*
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
class DashboardHeaderCellRenderer : ContainerRenderer<DashboardWidgetCell.DashboardHeaderCell>() {
    override val type: Int = R.layout.cell_dashboard_header

    override fun bindView(holder: ContainerHolder, model: DashboardWidgetCell.DashboardHeaderCell) {
        with(holder) {
            balanceTextView.text = model.balance
            currentPeriodTextView.text = model.currentPeriod
            incomesCardView.setAmountText(model.incomesAmount)
            expensesCardView.setAmountText(model.expensesAmount)
        }
    }

}