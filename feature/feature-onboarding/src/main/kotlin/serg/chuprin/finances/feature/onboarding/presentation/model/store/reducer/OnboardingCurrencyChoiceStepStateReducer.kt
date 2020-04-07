package serg.chuprin.finances.feature.onboarding.presentation.model.store.reducer

import serg.chuprin.finances.core.api.presentation.model.mvi.reducer.StoreStateReducer
import serg.chuprin.finances.feature.onboarding.presentation.model.store.OnboardingEffect
import serg.chuprin.finances.feature.onboarding.presentation.model.store.OnboardingStepState

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
class OnboardingCurrencyChoiceStepStateReducer :
    StoreStateReducer<OnboardingEffect.CurrencyChoice, OnboardingStepState.CurrencyChoiceState> {

    override fun invoke(
        effect: OnboardingEffect.CurrencyChoice,
        state: OnboardingStepState.CurrencyChoiceState
    ): OnboardingStepState.CurrencyChoiceState {
        return when (effect) {
            is OnboardingEffect.CurrencyChoice.CurrencyChosen -> {
                state.copy(
                    doneButtonIsEnabled = true,
                    currencyPickerIsVisible = false,
                    chosenCurrency = effect.currency,
                    chosenCurrencyDisplayName = effect.chosenCurrencyDisplayName
                )
            }
        }
    }

}