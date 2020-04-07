package serg.chuprin.finances.feature.onboarding.presentation.model.store.reducer

import serg.chuprin.finances.core.api.presentation.model.mvi.reducer.StoreStateReducer
import serg.chuprin.finances.feature.onboarding.presentation.model.store.OnboardingEffect
import serg.chuprin.finances.feature.onboarding.presentation.model.store.OnboardingState
import serg.chuprin.finances.feature.onboarding.presentation.model.store.OnboardingStepState

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
class OnboardingStateReducer(
    private val currencyChoiceStepStateReducer: OnboardingCurrencyChoiceStepStateReducer
) : StoreStateReducer<OnboardingEffect, OnboardingState> {

    override fun invoke(effect: OnboardingEffect, state: OnboardingState): OnboardingState {
        return when (effect) {
            is OnboardingEffect.CurrencyChoice -> {
                reduceCurrencyChoiceEffect(state, effect)
            }
            is OnboardingEffect.SetCurrentStepState -> {
                state.copy(stepState = effect.stepState)
            }
        }
    }

    private fun reduceCurrencyChoiceEffect(
        state: OnboardingState,
        effect: OnboardingEffect.CurrencyChoice
    ): OnboardingState {
        return if (state.stepState is OnboardingStepState.CurrencyChoiceState) {
            state.copy(stepState = currencyChoiceStepStateReducer(effect, state.stepState))
        } else {
            state
        }
    }

}