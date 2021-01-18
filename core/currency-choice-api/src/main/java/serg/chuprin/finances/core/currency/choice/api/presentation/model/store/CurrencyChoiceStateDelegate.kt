package serg.chuprin.finances.core.currency.choice.api.presentation.model.store

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import java.util.*

/**
 * Created by Sergey Chuprin on 04.07.2020.
 */
interface CurrencyChoiceStateDelegate {

    val chosenCurrency: Currency?

    val currencyPickerIsVisible: Boolean

    val availableCurrencies: List<Currency>

    val chosenCurrencyDisplayName: String

    /**
     * Current cells list could be filtered.
     */
    val currentCells: List<BaseCell>

    /**
     * Default list contains all cells unfiltered.
     * It is needed to replace current cells list after currency picker closing.
     */
    val defaultCurrencyCells: List<serg.chuprin.finances.core.currency.choice.api.presentation.model.cells.CurrencyCell>

}