package serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceIntent
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStore
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.model.AmountInputState
import serg.chuprin.finances.core.api.presentation.model.parser.AmountParser
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.core.mvi.executor.emptyFlowAction
import serg.chuprin.finances.core.mvi.invoke
import serg.chuprin.finances.feature.moneyaccount.creation.domain.usecase.CreateMoneyAccountUseCase
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
@ScreenScope
class MoneyAccountCreationActionExecutor @Inject constructor(
    private val amountParser: AmountParser,
    private val amountFormatter: AmountFormatter,
    private val currencyChoiceStore: CurrencyChoiceStore,
    private val createMoneyAccountUseCase: CreateMoneyAccountUseCase
) : StoreActionExecutor<MoneyAccountCreationAction, MoneyAccountCreationState, MoneyAccountCreationEffect, MoneyAccountCreationEvent> {

    override fun invoke(
        action: MoneyAccountCreationAction,
        state: MoneyAccountCreationState,
        eventConsumer: Consumer<MoneyAccountCreationEvent>,
        actionsFlow: Flow<MoneyAccountCreationAction>
    ): Flow<MoneyAccountCreationEffect> {
        return when (action) {
            is MoneyAccountCreationAction.ExecuteIntent -> {
                when (val intent = action.intent) {
                    MoneyAccountCreationIntent.BackPress -> {
                        handleBackPressIntent(state, eventConsumer)
                    }
                    MoneyAccountCreationIntent.ClickOnSaveButton -> {
                        handleClickOnSaveButtonIntent(state, eventConsumer)
                    }
                    is MoneyAccountCreationIntent.EnterAccountName -> {
                        handleEnterTitleIntent(intent)
                    }
                    is MoneyAccountCreationIntent.EnterBalance -> {
                        handleEnterAmountIntent(intent, state)
                    }
                }
            }
            is MoneyAccountCreationAction.UpdateCurrencyChoiceState -> {
                handleUpdateCurrencyChoiceStateAction(action)
            }
        }
    }

    private fun handleClickOnSaveButtonIntent(
        state: MoneyAccountCreationState,
        eventConsumer: Consumer<MoneyAccountCreationEvent>
    ): Flow<MoneyAccountCreationEffect> {
        if (state.savingButtonIsEnabled.not()) {
            return emptyFlow()
        }
        val initialBalance = state.balance ?: return emptyFlow()
        val chosenCurrency = state.chosenCurrency ?: return emptyFlow()
        return flow {
            createMoneyAccountUseCase.execute(
                currency = chosenCurrency,
                initialBalance = initialBalance,
                accountName = state.moneyAccountName
            )
            eventConsumer(MoneyAccountCreationEvent.ShowAccountCreatedMessage)
            eventConsumer(MoneyAccountCreationEvent.CloseScreen)
        }
    }

    private fun handleEnterTitleIntent(
        intent: MoneyAccountCreationIntent.EnterAccountName
    ): Flow<MoneyAccountCreationEffect> {
        return flowOfSingleValue {
            MoneyAccountCreationEffect.AccountNameEntered(intent.accountName)
        }
    }

    private fun handleEnterAmountIntent(
        intent: MoneyAccountCreationIntent.EnterBalance,
        state: MoneyAccountCreationState
    ): Flow<MoneyAccountCreationEffect> {
        return flowOfSingleValue {
            // Currency is always chosen.
            val formattedBalance = amountFormatter.formatInput(
                input = intent.balance,
                currency = state.chosenCurrency!!
            )
            val parsedBalance = amountParser.parse(formattedBalance)
            MoneyAccountCreationEffect.BalanceEntered(
                balance = parsedBalance,
                balanceInputState = AmountInputState(
                    hasError = parsedBalance == null,
                    formattedAmount = formattedBalance
                )
            )
        }
    }

    private fun handleBackPressIntent(
        state: MoneyAccountCreationState,
        eventConsumer: Consumer<MoneyAccountCreationEvent>
    ): Flow<MoneyAccountCreationEffect> {
        if (state.currencyPickerIsVisible) {
            return emptyFlowAction {
                currencyChoiceStore.dispatch(CurrencyChoiceIntent.ClickOnCloseCurrencyPicker)
            }
        }
        return emptyFlowAction {
            eventConsumer(MoneyAccountCreationEvent.CloseScreen)
        }
    }

    private fun handleUpdateCurrencyChoiceStateAction(
        action: MoneyAccountCreationAction.UpdateCurrencyChoiceState
    ): Flow<MoneyAccountCreationEffect> {
        return flowOfSingleValue {
            MoneyAccountCreationEffect.CurrencyChoiceStateUpdated(action.state)
        }
    }

}