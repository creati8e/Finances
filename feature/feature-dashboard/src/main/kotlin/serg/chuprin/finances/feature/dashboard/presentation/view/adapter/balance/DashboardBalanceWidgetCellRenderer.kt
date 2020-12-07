package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.balance

import kotlinx.android.synthetic.main.cell_widget_dashboard_balance.*
import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
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

    override fun bindView(holder: ContainerHolder, model: DashboardWidgetCell.Balance) {
        bindData(holder, model)
    }

    override fun bindView(
        holder: ContainerHolder,
        model: DashboardWidgetCell.Balance,
        payloads: MutableList<Any>
    ) {
        if (DashboardBalanceWidgetChangedPayload in payloads) {
            bindData(holder, model)
        }
    }

    override fun onVhCreated(
        holder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(holder) {
            incomesCardView.onClick(clickOnCurrentPeriodIncomes)
            expensesCardView.onClick(clickOnCurrentPeriodExpenses)
        }
    }

    private fun bindData(
        holder: ContainerHolder,
        model: DashboardWidgetCell.Balance
    ) {
        with(holder) {
            balanceTextView.text = model.balance
            incomesCardView.setAmountText(model.incomesAmount)
            expensesCardView.setAmountText(model.expensesAmount)
        }
    }

}