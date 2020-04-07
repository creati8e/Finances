package serg.chuprin.finances.feature.onboarding.presentation.model.store.executor

import androidx.core.util.Consumer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.presentation.model.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.feature.onboarding.presentation.model.store.*
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
class OnboardingActionExecutor @Inject constructor(
    private val currencyChoiceIntentExecutor: OnboardingCurrencyChoiceIntentExecutor
) : StoreActionExecutor<OnboardingAction, OnboardingState, OnboardingEffect, OnboardingEvent> {

    override fun invoke(
        action: OnboardingAction,
        state: OnboardingState,
        eventConsumer: Consumer<OnboardingEvent>
    ): Flow<OnboardingEffect> {
        return when (action) {
            is OnboardingAction.ExecuteIntent -> {
                when (val intent = action.intent) {
                    is OnboardingIntent.CurrencyChoice -> {
                        currencyChoiceIntentExecutor(
                            intent = intent,
                            eventConsumer = eventConsumer,
                            state = state.stepState as OnboardingStepState.CurrencyChoiceState
                        )
                    }
                }
            }
            is OnboardingAction.SetInitialStepState -> {
                handleSetInitialStepStateAction(action)
            }
        }
    }

    private fun handleSetInitialStepStateAction(
        action: OnboardingAction.SetInitialStepState
    ): Flow<OnboardingEffect> {
        return flowOf(OnboardingEffect.SetCurrentStepState(action.stepState))
    }

}