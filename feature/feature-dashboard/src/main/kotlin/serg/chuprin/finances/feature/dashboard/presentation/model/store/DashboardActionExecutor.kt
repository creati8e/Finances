package serg.chuprin.finances.feature.dashboard.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.feature.dashboard.presentation.model.builder.DashboardWidgetCellsBuilder
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
class DashboardActionExecutor @Inject constructor(
    private val widgetCellsBuilder: DashboardWidgetCellsBuilder
) : StoreActionExecutor<DashboardAction, DashboardState, DashboardEffect, DashboardEvent> {

    override fun invoke(
        action: DashboardAction,
        state: DashboardState,
        eventConsumer: Consumer<DashboardEvent>,
        actionsFlow: Flow<DashboardAction>
    ): Flow<DashboardEffect> {
        return when (action) {
            is DashboardAction.ExecuteIntent -> {
                when (val intent = action.intent) {
                    is DashboardIntent.ToggleMoneyAccountsVisibility -> {
                        handleToggleMoneyAccountsVisibilityIntent(intent, state)
                    }
                }
            }
            is DashboardAction.FormatDashboard -> {
                handleFormatDashboardAction(action, state)
            }
        }
    }

    private fun handleToggleMoneyAccountsVisibilityIntent(
        intent: DashboardIntent.ToggleMoneyAccountsVisibility,
        state: DashboardState
    ): Flow<DashboardEffect> {
        val updatedCells = state.cells.toMutableList().map { cell ->
            if (cell is DashboardWidgetCell.MoneyAccounts) {
                cell.copy(isExpanded = !intent.widgetCell.isExpanded)
            } else {
                cell
            }
        }
        return flowOf(DashboardEffect.CellsUpdated(updatedCells))
    }

    private fun handleFormatDashboardAction(
        action: DashboardAction.FormatDashboard,
        state: DashboardState
    ): Flow<DashboardEffect> {
        return flow {
            val widgetCells = widgetCellsBuilder.build(
                existingCells = state.cells,
                widgets = action.dashboard.widgets
            )
            emit(DashboardEffect.DashboardUpdated(action.dashboard, widgetCells))
        }
    }

}