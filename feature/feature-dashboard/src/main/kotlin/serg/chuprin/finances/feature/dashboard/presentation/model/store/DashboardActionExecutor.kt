package serg.chuprin.finances.feature.dashboard.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.model.DashboardDataPeriodChangeDirection
import serg.chuprin.finances.core.api.presentation.model.builder.DataPeriodTypePopupMenuCellsBuilder
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.core.mvi.executor.emptyFlowAction
import serg.chuprin.finances.core.mvi.invoke
import serg.chuprin.finances.feature.dashboard.domain.usecase.ChangeDashboardDataPeriodUseCase
import serg.chuprin.finances.feature.dashboard.domain.usecase.ChangeDataPeriodTypeForDashboardUseCase
import serg.chuprin.finances.feature.dashboard.domain.usecase.RestoreDefaultDashboardDataPeriodUseCase
import serg.chuprin.finances.feature.dashboard.presentation.model.builder.DashboardWidgetCellsBuilder
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
class DashboardActionExecutor @Inject constructor(
    private val widgetCellsBuilder: DashboardWidgetCellsBuilder,
    private val changeDataPeriodUseCase: ChangeDashboardDataPeriodUseCase,
    private val changeDataPeriodTypeUseCase: ChangeDataPeriodTypeForDashboardUseCase,
    private val periodTypePopupMenuCellsBuilder: DataPeriodTypePopupMenuCellsBuilder,
    private val restoreDefaultDataPeriodUseCase: RestoreDefaultDashboardDataPeriodUseCase
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
                    DashboardIntent.ClickOnNextPeriodButton -> {
                        handlePeriodChangeIntent(state, DashboardDataPeriodChangeDirection.NEXT)
                    }
                    DashboardIntent.ClickOnPreviousPeriodButton -> {
                        handlePeriodChangeIntent(state, DashboardDataPeriodChangeDirection.PREVIOUS)
                    }
                    DashboardIntent.ClickOnRestoreDefaultPeriodButton -> {
                        handleClickOnRestoreDefaultPeriodButton()
                    }
                    DashboardIntent.ClickOnCurrentPeriod -> {
                        handleClickOnCurrentPeriod(eventConsumer)
                    }
                    is DashboardIntent.ClickOnPeriodTypeCell -> {
                        handleClickOnPeriodTypeCell(intent)
                    }
                    is DashboardIntent.ClickOnMoneyAccount -> {
                        handleClickOnMoneyAccountIntent(intent, eventConsumer)
                    }
                    DashboardIntent.ClickOnMoneyAccountsListButton -> {
                        handleClickOnMoneyAccountsListButtonIntent(eventConsumer)
                    }
                }
            }
            is DashboardAction.FormatDashboard -> {
                handleFormatDashboardAction(action, state)
            }
        }
    }

    private fun handleClickOnMoneyAccountIntent(
        intent: DashboardIntent.ClickOnMoneyAccount,
        eventConsumer: Consumer<DashboardEvent>
    ): Flow<DashboardEffect> {
        return emptyFlowAction {
            val moneyAccountId = intent.cell.moneyAccount.id
            eventConsumer(DashboardEvent.NavigateToMoneyAccountDetailsScreen(moneyAccountId))
        }
    }

    private fun handleClickOnMoneyAccountsListButtonIntent(
        eventConsumer: Consumer<DashboardEvent>
    ): Flow<DashboardEffect> {
        return emptyFlowAction {
            eventConsumer(DashboardEvent.NavigateToMoneyAccountsListScreen)
        }
    }

    private fun handleClickOnPeriodTypeCell(
        intent: DashboardIntent.ClickOnPeriodTypeCell
    ): Flow<DashboardEffect> {
        return emptyFlowAction {
            changeDataPeriodTypeUseCase.execute(intent.periodTypeCell.periodType)
        }
    }

    private fun handleClickOnCurrentPeriod(
        eventConsumer: Consumer<DashboardEvent>
    ): Flow<DashboardEffect> {
        return emptyFlowAction {
            val cells = periodTypePopupMenuCellsBuilder.build()
            eventConsumer(DashboardEvent.ShowPeriodTypesPopupMenu(cells))
        }
    }

    private fun handleClickOnRestoreDefaultPeriodButton(): Flow<DashboardEffect> {
        return emptyFlowAction(restoreDefaultDataPeriodUseCase::execute)
    }

    private fun handlePeriodChangeIntent(
        state: DashboardState,
        changeDirection: DashboardDataPeriodChangeDirection
    ): Flow<DashboardEffect> {
        return emptyFlowAction {
            changeDataPeriodUseCase.execute(state.dashboard.currentDataPeriod, changeDirection)
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