package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceIntent
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceStore
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.core.mvi.executor.emptyFlowAction
import serg.chuprin.finances.core.mvi.invoke
import serg.chuprin.finances.feature.onboarding.domain.usecase.CompleteCurrencyChoiceOnboardingUseCase
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
class CurrencyChoiceOnboardingActionExecutor @Inject constructor(
    private val currencyChoiceStore: CurrencyChoiceStore,
    private val completeCurrencyChoiceOnboardingUseCase: CompleteCurrencyChoiceOnboardingUseCase
) : StoreActionExecutor<CurrencyChoiceOnboardingAction, CurrencyChoiceOnboardingState, CurrencyChoiceOnboardingEffect, CurrencyChoiceOnboardingEvent> {

    override fun invoke(
        action: CurrencyChoiceOnboardingAction,
        state: CurrencyChoiceOnboardingState,
        eventConsumer: Consumer<CurrencyChoiceOnboardingEvent>,
        actionsFlow: Flow<CurrencyChoiceOnboardingAction>
    ): Flow<CurrencyChoiceOnboardingEffect> {
        return when (action) {
            is CurrencyChoiceOnboardingAction.ExecuteIntent -> {
                when (action.intent) {
                    CurrencyChoiceOnboardingIntent.ClickOnDoneButton -> {
                        handleClickOnDoneButtonIntent(state, eventConsumer)
                    }
                    CurrencyChoiceOnboardingIntent.BackPress -> {
                        handleBackPressIntent(state, eventConsumer)
                    }
                }
            }
            is CurrencyChoiceOnboardingAction.UpdateCurrencyChoiceState -> {
                handleUpdateCurrencyChoiceState(action)
            }
        }
    }

    private fun handleUpdateCurrencyChoiceState(
        action: CurrencyChoiceOnboardingAction.UpdateCurrencyChoiceState
    ): Flow<CurrencyChoiceOnboardingEffect> {
        return flowOf(CurrencyChoiceOnboardingEffect.CurrencyChoiceStateUpdated(action.state))
    }

    private fun handleBackPressIntent(
        state: CurrencyChoiceOnboardingState,
        eventConsumer: Consumer<CurrencyChoiceOnboardingEvent>
    ): Flow<CurrencyChoiceOnboardingEffect> {
        if (state.currencyPickerIsVisible) {
            return emptyFlowAction {
                currencyChoiceStore.dispatch(CurrencyChoiceIntent.ClickOnCloseCurrencyPicker)
            }
        }
        return emptyFlowAction {
            eventConsumer(CurrencyChoiceOnboardingEvent.CloseApp)
        }
    }

    private fun handleClickOnDoneButtonIntent(
        state: CurrencyChoiceOnboardingState,
        eventConsumer: Consumer<CurrencyChoiceOnboardingEvent>
    ): Flow<CurrencyChoiceOnboardingEffect> {
        if (state.chosenCurrency == null) {
            return emptyFlow()
        }
        return flow {
            completeCurrencyChoiceOnboardingUseCase.execute(state.chosenCurrency)
            eventConsumer(CurrencyChoiceOnboardingEvent.NavigateToAccountsSetup)
        }
    }

}