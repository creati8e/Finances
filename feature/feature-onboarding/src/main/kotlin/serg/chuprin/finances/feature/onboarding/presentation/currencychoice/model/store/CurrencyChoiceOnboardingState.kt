package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store

import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.cells.CurrencyCell
import java.util.*

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
data class CurrencyChoiceOnboardingState(
    val chosenCurrency: Currency? = null,
    /**
     * Enabled if currency is chosen.
     */
    val doneButtonIsEnabled: Boolean = false,
    val currencyPickerIsVisible: Boolean = false,
    val availableCurrencies: List<Currency> = emptyList(),
    val chosenCurrencyDisplayName: String = EMPTY_STRING,
    /**
     * Current cells list could be filtered.
     */
    val currentCells: List<BaseCell> = emptyList(),
    /**
     * Default list contains all cells unfiltered.
     * It is needed to replace current cells list after currency picker closing.
     */
    val defaultCurrencyCells: List<CurrencyCell> = emptyList()
)