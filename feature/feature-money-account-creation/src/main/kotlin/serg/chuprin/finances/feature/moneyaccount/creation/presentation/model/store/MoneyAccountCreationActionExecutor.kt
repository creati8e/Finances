package serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceIntent
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStore
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.core.mvi.executor.emptyFlowAction
import serg.chuprin.finances.core.mvi.invoke
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
@ScreenScope
class MoneyAccountCreationActionExecutor @Inject constructor(
    private val currencyChoiceStore: CurrencyChoiceStore
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
                    MoneyAccountCreationIntent.ClickOnSaveButton -> TODO()
                }
            }
            is MoneyAccountCreationAction.UpdateCurrencyChoiceState -> {
                handleUpdateCurrencyChoiceStateAction(action)
            }
        }
    }

    private fun handleBackPressIntent(
        state: MoneyAccountCreationState,
        eventConsumer: Consumer<MoneyAccountCreationEvent>
    ): Flow<MoneyAccountCreationEffect> {
        if (state.currencyPickerIsVisible) {
            return emptyFlowAction {
                currencyChoiceStore.dispatch(CurrencyChoiceIntent.CloseCurrencyPicker)
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