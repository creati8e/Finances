package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store

import serg.chuprin.finances.core.mvi.reducer.StoreStateReducer

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
class CurrencyChoiceOnboardingStateReducer :
    StoreStateReducer<CurrencyChoiceOnboardingEffect, CurrencyChoiceOnboardingState> {

    override fun invoke(
        effect: CurrencyChoiceOnboardingEffect,
        state: CurrencyChoiceOnboardingState
    ): CurrencyChoiceOnboardingState {
        return when (effect) {
            is CurrencyChoiceOnboardingEffect.CurrencyChoiceStateUpdated -> {
                state.copy(
                    currencyChoiceState = effect.state,
                    doneButtonIsEnabled = effect.state.chosenCurrency != null
                )
            }
        }
    }

}