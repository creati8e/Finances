package serg.chuprin.finances.feature.moneyaccounts.presentation.model.store

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.api.presentation.model.cells.ZeroDataCell
import serg.chuprin.finances.core.api.presentation.model.formatter.AmountFormatter
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
    private val amountFormatter: AmountFormatter
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
                }
            }
        }
    }

    private fun handleClickOnMoneyAccountIntent(
        intent: MoneyAccountsListIntent.ClickOnMoneyAccount,
        eventConsumer: Consumer<MoneyAccountsListEvent>
    ): Flow<MoneyAccountsListEffect> {
        return emptyFlowAction {
            val moneyAccountId = intent.cell.moneyAccount.id
            eventConsumer(MoneyAccountsListEvent.NavigateToMoneyAccountDetailsScreen(moneyAccountId))
        }
    }

    private fun handleBuildMoneyAccountCellsAction(
        action: MoneyAccountsListAction.BuildMoneyAccountCells
    ): Flow<MoneyAccountsListEffect> {
        return flowOfSingleValue {
            val cells = if (action.moneyAccountBalances.isEmpty()) {
                listOf(
                    ZeroDataCell(
                        contentMessageRes = null,
                        iconRes = CoreR.drawable.ic_money_account,
                        titleRes = R.string.money_accounts_zero_data_title
                    )
                )
            } else {
                action.moneyAccountBalances.map { (moneyAccount, amount) ->
                    MoneyAccountCell(
                        name = moneyAccount.name,
                        moneyAccount = moneyAccount,
                        favoriteIconIsVisible = moneyAccount.isFavorite,
                        balance = amountFormatter.format(amount, moneyAccount.currency)
                    )
                }
            }
            MoneyAccountsListEffect.CellsBuilt(cells)
        }
    }

}