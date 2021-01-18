package serg.chuprin.finances.feature.moneyaccounts.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.api.presentation.builder.TransitionNameBuilder
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.model.cells.ZeroDataCell
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountDetailsScreenArguments
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountScreenArguments
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountsListScreenArguments
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.core.mvi.executor.emptyFlowAction
import serg.chuprin.finances.core.mvi.invoke
import serg.chuprin.finances.feature.moneyaccounts.R
import serg.chuprin.finances.feature.moneyaccounts.presentation.model.cells.MoneyAccountCell
import javax.inject.Inject
import serg.chuprin.finances.core.api.R as CoreR

/**
 * Created by Sergey Chuprin on 06.05.2020.
 */
class MoneyAccountsListActionExecutor @Inject constructor(
    private val amountFormatter: AmountFormatter,
    private val transitionNameBuilder: TransitionNameBuilder,
    private val screenArguments: MoneyAccountsListScreenArguments
) : StoreActionExecutor<MoneyAccountsListAction, MoneyAccountsListState, MoneyAccountsListEffect, MoneyAccountsListEvent> {

    override fun invoke(
        action: MoneyAccountsListAction,
        state: MoneyAccountsListState,
        eventConsumer: Consumer<MoneyAccountsListEvent>,
        actionsFlow: Flow<MoneyAccountsListAction>
    ): Flow<MoneyAccountsListEffect> {
        return when (action) {
            is MoneyAccountsListAction.BuildMoneyAccountCells -> {
                handleBuildMoneyAccountCellsAction(action)
            }
            is MoneyAccountsListAction.ExecuteIntent -> {
                when (val intent = action.intent) {
                    is MoneyAccountsListIntent.ClickOnMoneyAccount -> {
                        handleClickOnMoneyAccountIntent(intent, eventConsumer)
                    }
                    MoneyAccountsListIntent.ClickOnMoneyAccountCreationButton -> {
                        handleClickOnMoneyAccountCreationButtonIntent(eventConsumer)
                    }
                }
            }
            is MoneyAccountsListAction.ChangeAccountCreationButtonVisibility -> {
                handleChangeAccountCreationButtonVisibilityAction(action)
            }
        }
    }

    private fun handleChangeAccountCreationButtonVisibilityAction(
        action: MoneyAccountsListAction.ChangeAccountCreationButtonVisibility
    ): Flow<MoneyAccountsListEffect> {
        return flowOf(
            MoneyAccountsListEffect.AccountCreationButtonVisibilityChanged(action.isVisible)
        )
    }

    private fun handleClickOnMoneyAccountCreationButtonIntent(
        eventConsumer: Consumer<MoneyAccountsListEvent>
    ): Flow<MoneyAccountsListEffect> {
        return emptyFlowAction {
            val screenArguments = MoneyAccountScreenArguments.Creation(
                transitionNameBuilder.buildForMoneyAccountCreation()
            )
            eventConsumer(
                MoneyAccountsListEvent.NavigateToMoneyAccountCreationScreen(screenArguments)
            )
        }
    }

    private fun handleClickOnMoneyAccountIntent(
        intent: MoneyAccountsListIntent.ClickOnMoneyAccount,
        eventConsumer: Consumer<MoneyAccountsListEvent>
    ): Flow<MoneyAccountsListEffect> {
        if (screenArguments.isInPickerMode()) {
            return emptyFlowAction {
                eventConsumer(
                    MoneyAccountsListEvent.ChooseMoneyAccountAndCloseScreen(
                        intent.cell.moneyAccount.id
                    )
                )
            }
        }
        return emptyFlowAction {
            eventConsumer(
                MoneyAccountsListEvent.NavigateToMoneyAccountDetailsScreen(
                    MoneyAccountDetailsScreenArguments(
                        transitionName = intent.cell.transitionName,
                        moneyAccountId = intent.cell.moneyAccount.id
                    )
                )
            )
        }
    }

    private fun handleBuildMoneyAccountCellsAction(
        action: MoneyAccountsListAction.BuildMoneyAccountCells
    ): Flow<MoneyAccountsListEffect> {
        return flowOfSingleValue {
            val cells = if (action.moneyAccountToBalance.isEmpty()) {
                listOf(
                    ZeroDataCell(
                        contentMessageRes = null,
                        iconRes = CoreR.drawable.ic_money_account,
                        titleRes = R.string.money_accounts_zero_data_title
                    )
                )
            } else {
                action.moneyAccountToBalance.map { (moneyAccount, amount) ->
                    MoneyAccountCell(
                        name = moneyAccount.name,
                        moneyAccount = moneyAccount,
                        favoriteIconIsVisible = moneyAccount.isFavorite,
                        balance = amountFormatter.format(amount, moneyAccount.currency),
                        transitionName = transitionNameBuilder
                            .buildForForMoneyAccountDetails(moneyAccount.id)
                    )
                }
            }
            MoneyAccountsListEffect.CellsBuilt(cells)
        }
    }

}