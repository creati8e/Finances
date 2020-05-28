package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.header

import kotlinx.android.synthetic.main.cell_widget_dashboard_header.*
import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.adapter.LongClick
import serg.chuprin.finances.core.api.presentation.view.extensions.onClick
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.header.diff.DashboardHeaderWidgetChangedPayload

/**
 * Created by Sergey Chuprin on 28.05.2020.
 */
class DashboardHeaderWidgetCellRenderer(
    private val clickOnNextPeriod: () -> Unit,
    private val clickOnCurrentPeriod: () -> Unit,
    private val clickOnPreviousPeriod: () -> Unit,
    private val clickOnRestoreDefaultPeriod: () -> Unit,
    private val clickOnCurrentPeriodIncomes: () -> Unit,
    private val clickOnCurrentPeriodExpenses: () -> Unit
) : ContainerRenderer<DashboardWidgetCell.Header>() {

    override val type: Int = R.layout.cell_widget_dashboard_header

    override fun bindView(holder: ContainerHolder, model: DashboardWidgetCell.Header) {
        bindData(holder, model)
    }

    override fun bindView(
        holder: ContainerHolder,
        model: DashboardWidgetCell.Header,
        payloads: MutableList<Any>
    ) {
        if (DashboardHeaderWidgetChangedPayload in payloads) {
            bindData(holder, model)
        }
    }

    override fun onVhCreated(
        holder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(holder) {
            nextPeriodButton.onClick(clickOnNextPeriod)
            currentPeriodLayout.onClick(clickOnCurrentPeriod)
            previousPeriodButton.onClick(clickOnPreviousPeriod)
            incomesCardView.onClick(clickOnCurrentPeriodIncomes)
            expensesCardView.onClick(clickOnCurrentPeriodExpenses)
            restoreDefaultPeriodButton.onClick(clickOnRestoreDefaultPeriod)
        }
    }

    private fun bindData(
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