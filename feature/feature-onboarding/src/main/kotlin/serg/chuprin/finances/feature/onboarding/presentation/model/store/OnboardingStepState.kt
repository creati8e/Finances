package serg.chuprin.finances.feature.onboarding.presentation.model.store

import serg.chuprin.finances.core.api.domain.model.OnboardingStep
import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
import serg.chuprin.finances.feature.onboarding.presentation.model.cells.CurrencyCell
import java.util.*

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
sealed class OnboardingStepState(
    val step: OnboardingStep
) {

    data class CurrencyChoiceState(
        val doneButtonIsEnabled: Boolean = false,
        val currencyPickerIsVisible: Boolean = false,
        val currencyCells: List<CurrencyCell> = emptyList(),
        val chosenCurrencyDisplayName: String = EMPTY_STRING,
        val chosenCurrency: Currency = Currency.getInstance(Locale.getDefault())
    ) : OnboardingStepState(OnboardingStep.CURRENCY_CHOICE)

    object AccountsSetup : OnboardingStepState(OnboardingStep.ACCOUNT_SETUP)

}