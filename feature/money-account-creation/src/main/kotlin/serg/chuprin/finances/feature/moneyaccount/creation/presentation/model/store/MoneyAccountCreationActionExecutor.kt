package serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceIntent
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStore
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
                        handleEnterAmountIntent(intent)
                    }
                }
            }
            is MoneyAccountCreationAction.UpdateCurrencyChoiceState -> {
                handleUpdateCurrencyChoiceStateAction(action)
            }
            is MoneyAccountCreationAction.SetInitialStateForExistingAccount -> {
                handleSetInitialStateForExistingAccountAction(action)
            }
            MoneyAccountCreationAction.MakeCurrencyPickerClickable -> {
                handleMakeCurrencyPickerClickableAction()
            }
        }
    }

    private fun handleMakeCurrencyPickerClickableAction(): Flow<MoneyAccountCreationEffect> {
        return flowOf(MoneyAccountCreationEffect.CurrencyPickerClickabilityChanged(true))
    }

    private fun handleSetInitialStateForExistingAccountAction(
        action: MoneyAccountCreationAction.SetInitialStateForExistingAccount
    ): Flow<MoneyAccountCreationEffect> {
        return flowOfSingleValue {
            MoneyAccountCreationEffect.InitialStateForExistingAccountFormatted(
                balance = action.balance,
                accountName = action.accountName,
                moneyAccountDefaultData = action.moneyAccountDefaultData
            )
        }
    }

    private fun handleClickOnSaveButtonIntent(
        state: MoneyAccountCreationState,
        eventConsumer: Consumer<MoneyAccountCreationEvent>
    ): Flow<MoneyAccountCreationEffect> {
        if (state.savingButtonIsEnabled.not()) {
            return emptyFlow()
        }
        val balance = state.balance ?: return emptyFlow()
        val chosenCurrency = state.chosenCurrency ?: return emptyFlow()
        return flow {
            createMoneyAccountUseCase.execute(
                currency = chosenCurrency,
                initialBalance = balance,
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
        intent: MoneyAccountCreationIntent.EnterBalance
    ): Flow<MoneyAccountCreationEffect> {
        return flowOfSingleValue {
            MoneyAccountCreationEffect.BalanceEntered(amountParser.parse(intent.balance))
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