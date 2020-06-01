package serg.chuprin.finances.core.api.presentation.currencychoice.model.store

import serg.chuprin.finances.core.api.presentation.currencychoice.model.cells.CurrencyCell
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.cells.ZeroDataCell
import java.util.*

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
sealed class CurrencyChoiceEffect {

    /**
     * This effect is produced when currency chosen from list.
     * List could be filter or not.
     */
    class CurrencyChosen(
        val currency: Currency,
        val chosenCurrencyDisplayName: String,
        /**
         * All currencies, not only filtered.
         */
        val allCurrencyCellsWithChosen: List<CurrencyCell>
    ) : CurrencyChoiceEffect()

    class CurrenciesFilteredByQuery(
        /**
         * Contains filtered [CurrencyCell] or [ZeroDataCell] if currencies not found.
         */
        val currentCells: List<BaseCell>
    ) : CurrencyChoiceEffect()

    class CurrencyPickerVisibilityChanged(
        val visible: Boolean,
        /**
         * List of all currencies to replace current filtered cells list.
         */
        val currentCells: List<CurrencyCell>
    ) : CurrencyChoiceEffect()

    /**
     * Initial effect produced as result of store bootstrapping.
     */
    class SetCurrencyParams(
        val currentCurrency: Currency,
        val chosenCurrencyDisplayName: String,
        val availableCurrencies: List<Currency>,
        val allCurrencyCellsWithChosen: List<CurrencyCell>
    ) : CurrencyChoiceEffect()

}