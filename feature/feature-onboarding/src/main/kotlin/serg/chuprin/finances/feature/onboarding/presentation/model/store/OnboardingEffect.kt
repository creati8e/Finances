package serg.chuprin.finances.feature.onboarding.presentation.model.store

import java.util.*

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
sealed class OnboardingEffect {

    sealed class CurrencyChoice : OnboardingEffect() {

        class CurrencyChosen(
            val currency: Currency,
            val chosenCurrencyDisplayName: String
        ) : CurrencyChoice()

    }

    class SetCurrentStepState(
        val stepState: OnboardingStepState
    ) : OnboardingEffect()

}