package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store

import serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.cells.CurrencyCell
import java.util.*

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
sealed class CurrencyChoiceOnboardingEffect {

    class CurrencyChosen(
        val currency: Currency,
        val chosenCurrencyDisplayName: String,
        val updatedCurrencyCells: List<CurrencyCell>
    ) : CurrencyChoiceOnboardingEffect()

    class CurrenciesFilteredByQuery(
        val currencyCells: List<CurrencyCell>
    ) : CurrencyChoiceOnboardingEffect()

    class CurrencyPickerVisibilityChanged(
        val visible: Boolean
    ) : CurrencyChoiceOnboardingEffect()

    class SetCurrencyParams(
        val currentCurrency: Currency,
        val chosenCurrencyDisplayName: String,
        val currencyCells: List<CurrencyCell>,
        val availableCurrencies: List<Currency>
    ) : CurrencyChoiceOnboardingEffect()

}