package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store

import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceState
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceStateDelegate

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
data class CurrencyChoiceOnboardingState(
    /**
     * Enabled if currency is chosen.
     */
    val doneButtonIsEnabled: Boolean = false,
    val currencyChoiceState: CurrencyChoiceState = CurrencyChoiceState()
) : CurrencyChoiceStateDelegate by currencyChoiceState