package serg.chuprin.finances.core.currency.choice.api.presentation.model.store

import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import java.util.*

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
data class CurrencyChoiceState(
    override val chosenCurrency: Currency? = null,
    override val currencyPickerIsVisible: Boolean = false,
    override val availableCurrencies: List<Currency> = emptyList(),
    override val chosenCurrencyDisplayName: String = EMPTY_STRING,
    /**
     * Current cells list could be filtered.
     */
    override val currentCells: List<BaseCell> = emptyList(),
    /**
     * Default list contains all cells unfiltered.
     * It is needed to replace current cells list after currency picker closing.
     */
    override val defaultCurrencyCells: List<serg.chuprin.finances.core.currency.choice.api.presentation.model.cells.CurrencyCell> = emptyList()
) : CurrencyChoiceStateDelegate