package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.cells.ZeroDataCell
import serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.cells.CurrencyCell
import java.util.*

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
sealed class CurrencyChoiceOnboardingEffect {

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
    ) : CurrencyChoiceOnboardingEffect()

    class CurrenciesFilteredByQuery(
        /**
         * Contains filtered [CurrencyCell] or [ZeroDataCell] if currencies not found.
         */
        val cells: List<BaseCell>
    ) : CurrencyChoiceOnboardingEffect()

    class CurrencyPickerVisibilityChanged(
        val visible: Boolean,
        /**
         * List of all currencies to replace current filtered cells list.
         */
        val allCurrencyCells: List<CurrencyCell>
    ) : CurrencyChoiceOnboardingEffect()

    class UserCreationInProgress(
        val progressVisible: Boolean
    ) : CurrencyChoiceOnboardingEffect()

    /**
     * Initial effect produced as result of store bootstrapping.
     */
    class SetCurrencyParams(
        val currentCurrency: Currency,
        val chosenCurrencyDisplayName: String,
        val currencyCells: List<CurrencyCell>,
        val availableCurrencies: List<Currency>
    ) : CurrencyChoiceOnboardingEffect()

}