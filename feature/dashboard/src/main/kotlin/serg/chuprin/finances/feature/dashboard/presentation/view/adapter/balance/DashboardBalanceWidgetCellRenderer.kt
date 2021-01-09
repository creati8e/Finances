package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.balance

import kotlinx.android.synthetic.main.cell_widget_dashboard_balance.*
import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.ContainerRenderer
import serg.chuprin.adapter.LongClick
import serg.chuprin.finances.core.api.presentation.view.extensions.onClick
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.balance.diff.DashboardBalanceWidgetChangedPayload

/**
 * Created by Sergey Chuprin on 28.05.2020.
 */
class DashboardBalanceWidgetCellRenderer(
    private val clickOnCurrentPeriodIncomes: () -> Unit,
    private val clickOnCurrentPeriodExpenses: () -> Unit
) : ContainerRenderer<DashboardWidgetCell.Balance>() {

    override val type: Int = R.layout.cell_widget_dashboard_balance

    override fun bindView(viewHolder: ContainerHolder, cell: DashboardWidgetCell.Balance) {
        bindData(viewHolder, cell)
    }

    override fun bindView(
        viewHolder: ContainerHolder,
        cell: DashboardWidgetCell.Balance,
        payloads: MutableList<Any>
    ) {
        if (DashboardBalanceWidgetChangedPayload in payloads) {
            bindData(viewHolder, cell)
        }
    }

    override fun onVhCreated(
        viewHolder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(viewHolder) {
            incomesCardView.onClick(clickOnCurrentPeriodIncomes)
            expensesCardView.onClick(clickOnCurrentPeriodExpenses)
        }
    }

    private fun bindData(
        viewHolder: ContainerHolder,
        cell: DashboardWidgetCell.Balance
    ) {
        with(viewHolder) {
            balanceTextView.text = cell.balance
            incomesCardView.setAmountText(cell.incomesAmount)
            expensesCardView.setAmountText(cell.expensesAmount)
        }
    }

}