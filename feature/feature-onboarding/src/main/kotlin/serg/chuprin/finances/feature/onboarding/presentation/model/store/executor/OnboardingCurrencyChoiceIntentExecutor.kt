package serg.chuprin.finances.feature.onboarding.presentation.model.store.executor

import androidx.core.util.Consumer
import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.presentation.model.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.feature.onboarding.presentation.model.store.OnboardingEffect
import serg.chuprin.finances.feature.onboarding.presentation.model.store.OnboardingEvent
import serg.chuprin.finances.feature.onboarding.presentation.model.store.OnboardingIntent
import serg.chuprin.finances.feature.onboarding.presentation.model.store.OnboardingStepState
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
class OnboardingCurrencyChoiceIntentExecutor @Inject constructor() :
    StoreActionExecutor<OnboardingIntent.CurrencyChoice, OnboardingStepState.CurrencyChoiceState, OnboardingEffect, OnboardingEvent> {

    override fun invoke(
        intent: OnboardingIntent.CurrencyChoice,
        state: OnboardingStepState.CurrencyChoiceState,
        eventConsumer: Consumer<OnboardingEvent>
    ): Flow<OnboardingEffect> {
        return when (intent) {
            OnboardingIntent.CurrencyChoice.ClickOnDoneButton -> {
                handleClickOnDoneButtonIntent(intent, eventConsumer)
            }
            OnboardingIntent.CurrencyChoice.ClickOnCurrencyPicker -> TODO()
        }
    }

    private fun handleClickOnDoneButtonIntent(
        intent: OnboardingIntent.CurrencyChoice,
        eventConsumer: Consumer<OnboardingEvent>
    ): Flow<OnboardingEffect> {
        TODO()
    }

}