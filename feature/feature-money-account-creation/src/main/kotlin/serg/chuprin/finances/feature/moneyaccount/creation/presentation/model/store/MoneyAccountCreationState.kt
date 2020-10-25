package serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store

import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceState
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStateDelegate

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
data class MoneyAccountCreationState(
    val creationButtonIsEnabled: Boolean = false,
    val currencyChoiceState: CurrencyChoiceState = CurrencyChoiceState()
) : CurrencyChoiceStateDelegate by currencyChoiceState