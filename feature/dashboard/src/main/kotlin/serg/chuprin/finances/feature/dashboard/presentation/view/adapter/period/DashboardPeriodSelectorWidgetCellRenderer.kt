package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.period

import kotlinx.android.synthetic.main.cell_widget_dashboard_period_selector.*
import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.ContainerRenderer
import serg.chuprin.adapter.LongClick
import serg.chuprin.finances.core.api.presentation.view.extensions.onClick
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.period.diff.DashboardPeriodSelectorWidgetCellChangedPayload

/**
 * Created by Sergey Chuprin on 07.12.2020.
 */
class DashboardPeriodSelectorWidgetCellRenderer(
    private val clickOnNextPeriod: () -> Unit,
    private val clickOnCurrentPeriod: () -> Unit,
    private val clickOnPreviousPeriod: () -> Unit,
    private val clickOnRestoreDefaultPeriod: () -> Unit,
) : ContainerRenderer<DashboardWidgetCell.PeriodSelector>() {

    override val type: Int = R.layout.cell_widget_dashboard_period_selector

    override fun bindView(viewHolder: ContainerHolder, cell: DashboardWidgetCell.PeriodSelector) {
        bindData(viewHolder, cell)
    }

    override fun bindView(
        viewHolder: ContainerHolder,
        cell: DashboardWidgetCell.PeriodSelector,
        payloads: MutableList<Any>
    ) {
        if (DashboardPeriodSelectorWidgetCellChangedPayload in payloads) {
            bindData(viewHolder, cell)
        }
    }

    override fun onVhCreated(
        viewHolder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(viewHolder) {
            nextPeriodButton.onClick(clickOnNextPeriod)
            currentPeriodLayout.onClick(clickOnCurrentPeriod)
            previousPeriodButton.onClick(clickOnPreviousPeriod)
            restoreDefaultPeriodButton.onClick(clickOnRestoreDefaultPeriod)
        }
    }

    private fun bindData(
        viewHolder: ContainerHolder,
        cell: DashboardWidgetCell.PeriodSelector
    ) {
        with(viewHolder) {
            currentPeriodTextView.text = cell.currentPeriod
        }
    }

}