package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store

import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
import serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.cells.CurrencyCell
import java.util.*

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
data class CurrencyChoiceOnboardingState(
    val chosenCurrency: Currency? = null,
    val doneButtonIsEnabled: Boolean = false,
    val currencyPickerIsVisible: Boolean = false,
    val currencyCells: List<CurrencyCell> = emptyList(),
    val availableCurrencies: List<Currency> = emptyList(),
    val chosenCurrencyDisplayName: String = EMPTY_STRING
)