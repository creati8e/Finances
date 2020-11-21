package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store

import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceState

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
sealed class CurrencyChoiceOnboardingEffect {

    class CurrencyChoiceStateUpdated(
        val state: CurrencyChoiceState
    ) : CurrencyChoiceOnboardingEffect()

}