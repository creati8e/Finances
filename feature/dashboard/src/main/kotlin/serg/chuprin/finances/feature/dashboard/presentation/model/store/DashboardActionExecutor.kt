package serg.chuprin.finances.feature.dashboard.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.presentation.builder.TransitionNameBuilder
import serg.chuprin.finances.core.api.presentation.model.builder.DataPeriodTypePopupMenuCellsBuilder
import serg.chuprin.finances.core.api.presentation.model.cells.ZeroDataCell
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountDetailsScreenArguments
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountScreenArguments
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountsListScreenArguments
import serg.chuprin.finances.core.api.presentation.screen.arguments.TransactionScreenArguments
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.core.mvi.executor.emptyFlowAction
import serg.chuprin.finances.core.mvi.invoke
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardDataPeriodChangeDirection
import serg.chuprin.finances.feature.dashboard.domain.usecase.ChangeDashboardDataPeriodUseCase
import serg.chuprin.finances.feature.dashboard.domain.usecase.ChangeDataPeriodTypeForDashboardUseCase
import serg.chuprin.finances.feature.dashboard.domain.usecase.RestoreDefaultDashboardDataPeriodUseCase
import serg.chuprin.finances.feature.dashboard.presentation.model.builder.DashboardTransactionsReportArgumentsBuilder
import serg.chuprin.finances.feature.dashboard.presentation.model.builder.DashboardWidgetCellsBuilder
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import javax.inject.Inject
import serg.chuprin.finances.core.api.R as CoreR

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
class DashboardActionExecutor @Inject constructor(
    private val resourceManger: ResourceManger,
    private val transitionNameBuilder: TransitionNameBuilder,
    private val widgetCellsBuilder: DashboardWidgetCellsBuilder,
    private val changeDataPeriodUseCase: ChangeDashboardDataPeriodUseCase,
    private val reportArgumentsBuilder: DashboardTransactionsReportArgumentsBuilder,
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
                    DashboardIntent.ClickOnCreateMoneyAccountButton -> {
                        handleClickOnCreateMoneyAccountButtonIntent(eventConsumer)
                    }
                    DashboardIntent.ClickOnCurrentPeriodIncomesButton -> {
                        handleClickOnCurrentPeriodIncomesButtonIntent(state, eventConsumer)
                    }
                    DashboardIntent.ClickOnCurrentPeriodExpensesButton -> {
                        handleClickOnCurrentPeriodExpensesButtonIntent(state, eventConsumer)
                    }
                    DashboardIntent.ClickOnShowMoreTransactionsButton -> {
                        handleClickOnShowMoreTransactionsButton(state, eventConsumer)
                    }
                    is DashboardIntent.ClickOnCategory -> {
                        handleClickOnCategoryIntent(intent, state, eventConsumer)
                    }
                    DashboardIntent.ClickOnZeroData -> {
                        handleClickOnZeroDataIntent(eventConsumer)
                    }
                    DashboardIntent.ClickOnTransactionCreationButton -> {
                        handleClickOnTransactionCreationButtonIntent(eventConsumer)
                    }
                    is DashboardIntent.ClickOnRecentTransactionCell -> {
                        handleClickOnRecentTransactionCellIntent(intent, eventConsumer)
                    }
                }
            }
            is DashboardAction.FormatDashboard -> {
                handleFormatDashboardAction(action, state)
            }
        }
    }

    private fun handleClickOnRecentTransactionCellIntent(
        intent: DashboardIntent.ClickOnRecentTransactionCell,
        eventConsumer: Consumer<DashboardEvent>
    ): Flow<DashboardEffect> {
        return emptyFlowAction {
            val transitionName = transitionNameBuilder.buildForTransaction(
                intent.transactionCell.transaction.id
            )
            val screenArguments = TransactionScreenArguments.Editing(
                transitionName = transitionName,
                transactionId = intent.transactionCell.transaction.id
            )
            eventConsumer(DashboardEvent.NavigateToTransactionScreen(screenArguments))
        }
    }

    private fun handleClickOnTransactionCreationButtonIntent(
        eventConsumer: Consumer<DashboardEvent>
    ): Flow<DashboardEffect> {
        return emptyFlowAction {
            val transitionName = transitionNameBuilder.buildForTransaction()
            val screenArguments = TransactionScreenArguments.Creation(transitionName)
            eventConsumer(DashboardEvent.NavigateToTransactionScreen(screenArguments))
        }
    }

    private fun handleClickOnZeroDataIntent(
        eventConsumer: Consumer<DashboardEvent>
    ): Flow<DashboardEffect> {
        return emptyFlowAction {
            eventConsumer(buildMoneyAccountCreationEvent())
        }
    }

    private fun handleClickOnCategoryIntent(
        intent: DashboardIntent.ClickOnCategory,
        state: DashboardState,
        eventConsumer: Consumer<DashboardEvent>
    ): Flow<DashboardEffect> {
        return emptyFlowAction {
            val arguments = reportArgumentsBuilder.buildForCategory(intent.cell, state)
            eventConsumer(DashboardEvent.NavigateToTransactionsReportScreen(arguments))
        }
    }

    private fun handleClickOnShowMoreTransactionsButton(
        state: DashboardState,
        eventConsumer: Consumer<DashboardEvent>
    ): Flow<DashboardEffect> {
        return emptyFlowAction {
            val arguments = reportArgumentsBuilder.buildForAllTransactions(state)
            eventConsumer(DashboardEvent.NavigateToTransactionsReportScreen(arguments))
        }
    }

    private fun handleClickOnCurrentPeriodExpensesButtonIntent(
        state: DashboardState,
        eventConsumer: Consumer<DashboardEvent>
    ): Flow<DashboardEffect> {
        return emptyFlowAction {
            val arguments = reportArgumentsBuilder.buildForPeriodExpenses(state)
            eventConsumer(DashboardEvent.NavigateToTransactionsReportScreen(arguments))
        }
    }

    private fun handleClickOnCurrentPeriodIncomesButtonIntent(
        state: DashboardState,
        eventConsumer: Consumer<DashboardEvent>
    ): Flow<DashboardEffect> {
        return emptyFlowAction {
            val arguments = reportArgumentsBuilder.buildForPeriodIncome(state)
            eventConsumer(DashboardEvent.NavigateToTransactionsReportScreen(arguments))
        }
    }

    private fun handleClickOnCreateMoneyAccountButtonIntent(
        eventConsumer: Consumer<DashboardEvent>
    ): Flow<DashboardEffect> {
        return emptyFlowAction {
            eventConsumer(buildMoneyAccountCreationEvent())
        }
    }

    private fun handleClickOnMoneyAccountIntent(
        intent: DashboardIntent.ClickOnMoneyAccount,
        eventConsumer: Consumer<DashboardEvent>
    ): Flow<DashboardEffect> {
        return emptyFlowAction {
            eventConsumer(
                DashboardEvent.NavigateToMoneyAccountDetailsScreen(
                    MoneyAccountDetailsScreenArguments(
                        transitionName = intent.cell.transitionName,
                        moneyAccountId = intent.cell.moneyAccount.id
                    )
                )
            )
        }
    }

    private fun handleClickOnMoneyAccountsListButtonIntent(
        eventConsumer: Consumer<DashboardEvent>
    ): Flow<DashboardEffect> {
        return emptyFlowAction {
            val screenArguments = MoneyAccountsListScreenArguments.Editing
            eventConsumer(DashboardEvent.NavigateToMoneyAccountsListScreen(screenArguments))
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
            if (action.dashboard.hasNoMoneyAccounts) {
                emit(
                    DashboardEffect.DashboardUpdated(
                        hasNoMoneyAccounts = true,
                        dashboard = action.dashboard,
                        cells = listOf(buildNoMoneyAccountsZeroDataCell())
                    )
                )
            } else {
                val widgetCells = widgetCellsBuilder.build(
                    existingCells = state.cells,
                    widgets = action.dashboard.widgets
                )
                emit(
                    DashboardEffect.DashboardUpdated(
                        cells = widgetCells,
                        hasNoMoneyAccounts = false,
                        dashboard = action.dashboard
                    )
                )
            }
        }
    }

    private fun buildMoneyAccountCreationEvent(): DashboardEvent.NavigateToMoneyAccountCreationScreen {
        val transitionName = transitionNameBuilder.buildForMoneyAccountCreation()
        val screenArguments = MoneyAccountScreenArguments.Creation(transitionName)
        return DashboardEvent.NavigateToMoneyAccountCreationScreen(screenArguments)
    }


    private fun buildNoMoneyAccountsZeroDataCell() = ZeroDataCell(
        contentMessageRes = null,
        iconRes = R.drawable.ic_account,
        titleRes = R.string.dashboard_no_money_accounts_zero_data_title,
        buttonRes = R.string.dashboard_no_money_accounts_zero_data_button,
        buttonTransitionName = resourceManger.getString(
            CoreR.string.transition_money_account
        )
    )

}