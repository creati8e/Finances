package serg.chuprin.finances.feature.dashboard.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.model.orUnknown
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.presentation.model.DataPeriodUi
import serg.chuprin.finances.core.api.presentation.model.builder.DataPeriodTypePopupMenuCellsBuilder
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.api.presentation.screen.arguments.TransactionsReportScreenArguments
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.core.mvi.executor.emptyFlowAction
import serg.chuprin.finances.core.mvi.invoke
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardDataPeriodChangeDirection
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import serg.chuprin.finances.feature.dashboard.domain.usecase.ChangeDashboardDataPeriodUseCase
import serg.chuprin.finances.feature.dashboard.domain.usecase.ChangeDataPeriodTypeForDashboardUseCase
import serg.chuprin.finances.feature.dashboard.domain.usecase.RestoreDefaultDashboardDataPeriodUseCase
import serg.chuprin.finances.feature.dashboard.presentation.model.builder.DashboardWidgetCellsBuilder
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.setup.presentation.domain.model.DashboardWidgetType
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
class DashboardActionExecutor @Inject constructor(
    private val resourceManger: ResourceManger,
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
                }
            }
            is DashboardAction.FormatDashboard -> {
                handleFormatDashboardAction(action, state)
            }
        }
    }

    // TODO: Extract to builder.
    private fun handleClickOnCategoryIntent(
        intent: DashboardIntent.ClickOnCategory,
        state: DashboardState,
        eventConsumer: Consumer<DashboardEvent>
    ): Flow<DashboardEffect> {
        return emptyFlowAction {
            val categoryCell = intent.cell
            val dataPeriod = DataPeriodUi.create(state.dashboard.currentDataPeriod)

            val arguments = if (categoryCell.isOtherCategory) {
                val key = DashboardWidgetType.CATEGORIES
                val widget = (state.dashboard.widgets.getValue(key) as DashboardWidget.Categories)
                val pages = widget.pages
                val page = pages.first { it.transactionType == categoryCell.plainTransactionType }
                val categoryIds = page.otherAmounts!!.mapNotNullTo(ArrayList(), { (category) ->
                    category?.id?.value
                })

                TransactionsReportScreenArguments.Category.Other(
                    dataPeriodUi = dataPeriod,
                    categoryIds = categoryIds,
                    transitionName = categoryCell.transitionName,
                    transactionType = categoryCell.plainTransactionType
                )
            } else {
                TransactionsReportScreenArguments.Category.Concrete(
                    dataPeriodUi = dataPeriod,
                    transitionName = categoryCell.transitionName,
                    categoryId = categoryCell.category?.id.orUnknown(),
                    transactionType = categoryCell.plainTransactionType
                )
            }
            eventConsumer(DashboardEvent.NavigateToTransactionsReportScreen(arguments))
        }
    }

    private fun handleClickOnShowMoreTransactionsButton(
        state: DashboardState,
        eventConsumer: Consumer<DashboardEvent>
    ): Flow<DashboardEffect> {
        return emptyFlowAction {
            val arguments = TransactionsReportScreenArguments.AllTransactions(
                dataPeriodUi = DataPeriodUi.create(state.dashboard.currentDataPeriod),
                transitionName = getString(
                    R.string.transition_dashboard_recent_transactions_to_transactions_report
                )
            )
            eventConsumer(DashboardEvent.NavigateToTransactionsReportScreen(arguments))
        }
    }

    private fun handleClickOnCurrentPeriodExpensesButtonIntent(
        state: DashboardState,
        eventConsumer: Consumer<DashboardEvent>
    ): Flow<DashboardEffect> {
        return emptyFlowAction {
            val dataPeriod = DataPeriodUi.create(state.dashboard.currentDataPeriod)
            val arguments = TransactionsReportScreenArguments.Transactions(
                dataPeriodUi = dataPeriod,
                transactionType = PlainTransactionType.EXPENSE,
                transitionName = getString(
                    R.string.transition_dashboard_to_transactions_report_expenses
                )
            )
            eventConsumer(DashboardEvent.NavigateToTransactionsReportScreen(arguments))
        }
    }

    private fun handleClickOnCurrentPeriodIncomesButtonIntent(
        state: DashboardState,
        eventConsumer: Consumer<DashboardEvent>
    ): Flow<DashboardEffect> {
        return emptyFlowAction {
            val dataPeriod = DataPeriodUi.create(state.dashboard.currentDataPeriod)
            val arguments = TransactionsReportScreenArguments.Transactions(
                dataPeriodUi = dataPeriod,
                transactionType = PlainTransactionType.INCOME,
                transitionName = getString(
                    R.string.transition_dashboard_to_transactions_report_incomes
                )
            )
            eventConsumer(DashboardEvent.NavigateToTransactionsReportScreen(arguments))
        }
    }

    private fun handleClickOnCreateMoneyAccountButtonIntent(
        eventConsumer: Consumer<DashboardEvent>
    ): Flow<DashboardEffect> {
        return emptyFlowAction {
            eventConsumer(DashboardEvent.NavigateToMoneyAccountCreationScreen)
        }
    }

    private fun handleClickOnMoneyAccountIntent(
        intent: DashboardIntent.ClickOnMoneyAccount,
        eventConsumer: Consumer<DashboardEvent>
    ): Flow<DashboardEffect> {
        return emptyFlowAction {
            eventConsumer(
                DashboardEvent.NavigateToMoneyAccountDetailsScreen(
                    transitionName = intent.cell.transitionName,
                    moneyAccountId = intent.cell.moneyAccount.id
                )
            )
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

    private fun getString(stringRes: Int): String = resourceManger.getString(stringRes)

}