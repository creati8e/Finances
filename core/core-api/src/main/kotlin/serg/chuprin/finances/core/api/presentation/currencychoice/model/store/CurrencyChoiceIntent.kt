package serg.chuprin.finances.core.api.presentation.currencychoice.model.store

import serg.chuprin.finances.core.api.presentation.currencychoice.model.cells.CurrencyCell

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
sealed class CurrencyChoiceIntent {

    object CloseCurrencyPicker : CurrencyChoiceIntent()

    /**
     * User want to open currency picker.
     */
    object ClickOnCurrencyPicker : CurrencyChoiceIntent()

    class SearchCurrencies(
        val searchQuery: String
    ) : CurrencyChoiceIntent()

    /**
     * User has chosen a particular currency from currencies list.
     */
    class ChooseCurrency(
        val currencyCell: CurrencyCell
    ) : CurrencyChoiceIntent()

}