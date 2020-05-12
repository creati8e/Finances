package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.dsl.header

import kotlinx.android.synthetic.main.cell_widget_dashboard_header.*
import serg.chuprin.finances.core.api.presentation.view.adapter.dsl.context.RecyclerViewAdapterContext
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.presentation.model.store.DashboardIntent
import serg.chuprin.finances.feature.dashboard.presentation.model.viewmodel.DashboardViewModel

/**
 * Created by Sergey Chuprin on 29.04.2020.
 */
fun RecyclerViewAdapterContext.setupHeaderWidgetBinding(viewModel: DashboardViewModel) {
    add<DashboardWidgetCell.Header>(
        R.layout.cell_widget_dashboard_header
    ) {
        bind { cell, _ ->
            balanceTextView.text = cell.balance
            currentPeriodTextView.text = cell.currentPeriod
            incomesCardView.setAmountText(cell.incomesAmount)
            expensesCardView.setAmountText(cell.expensesAmount)
        }
        setupViews {
            setClickListener(
                currentPeriodLayout,
                { viewModel.dispatchIntent(DashboardIntent.ClickOnCurrentPeriod) }
            )
            setClickListener(
                nextPeriodButton,
                { viewModel.dispatchIntent(DashboardIntent.ClickOnNextPeriodButton) }
            )
            setClickListener(
                previousPeriodButton,
                { viewModel.dispatchIntent(DashboardIntent.ClickOnPreviousPeriodButton) }
            )
            setClickListener(
                restoreDefaultPeriodButton,
                { viewModel.dispatchIntent(DashboardIntent.ClickOnRestoreDefaultPeriodButton) }
            )
            setClickListener(
                expensesCardView,
                { viewModel.dispatchIntent(DashboardIntent.ClickOnCurrentPeriodExpensesButton) }
            )
            setClickListener(
                incomesCardView,
                { viewModel.dispatchIntent(DashboardIntent.ClickOnCurrentPeriodIncomesButton) }
            )
        }
    }
}

/**
 * Represents the whole widget change event.
 */
object DashboardHeaderWidgetChangedPayload