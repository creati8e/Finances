package serg.chuprin.finances.core.currency.choice.impl.presentation.model.store

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.cells.ZeroDataCell
import serg.chuprin.finances.core.currency.choice.api.presentation.model.cells.CurrencyCell
import java.util.*

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
sealed class CurrencyChoiceEffect {

    /**
     * This effect is produced when currency was chosen from list.
     * List could be filtered or not.
     */
    class CurrencyChosen(
        val currency: Currency,
        val chosenCurrencyDisplayName: String,
        /**
         * All cells, not just filtered.
         */
        val unfilteredCells: List<CurrencyCell>
    ) : CurrencyChoiceEffect()

    class CurrenciesFilteredByQuery(
        /**
         * Contains filtered [CurrencyCell] or [ZeroDataCell] if currencies not found.
         */
        val filteredCells: List<BaseCell>
    ) : CurrencyChoiceEffect()

    class CurrencyPickerVisibilityChanged(
        val visible: Boolean,
        /**
         * List of all currencies to replace current filtered cells list.
         */
        val unfilteredCells: List<CurrencyCell>
    ) : CurrencyChoiceEffect()

    /**
     * Initial effect produced as result of store bootstrapping.
     */
    class SetCurrencyParams(
        val chosenCurrency: Currency,
        val chosenCurrencyDisplayName: String,
        val availableCurrencies: List<Currency>,
        val unfilteredCells: List<CurrencyCell>
    ) : CurrencyChoiceEffect()

}