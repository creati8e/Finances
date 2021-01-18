package serg.chuprin.finances.core.currency.choice.impl.presentation.model.store

import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceIntent
import java.util.*

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
sealed class CurrencyChoiceAction {

    class SetCurrenciesParams(
        val chosenCurrency: Currency,
        val availableCurrencies: List<Currency>
    ) : CurrencyChoiceAction()

    class ExecuteIntent(
        val intent: CurrencyChoiceIntent
    ) : CurrencyChoiceAction()

}