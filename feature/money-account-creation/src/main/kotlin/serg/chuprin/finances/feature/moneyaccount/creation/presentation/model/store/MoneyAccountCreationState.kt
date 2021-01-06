package serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store

import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceState
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStateDelegate
import serg.chuprin.finances.core.api.presentation.model.AmountInputState
import java.math.BigDecimal

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
data class MoneyAccountCreationState(
    val balance: BigDecimal? = null,
    val moneyAccountName: String = EMPTY_STRING,
    val savingButtonIsEnabled: Boolean = false,
    val balanceInputState: AmountInputState = AmountInputState(),
    val currencyChoiceState: CurrencyChoiceState = CurrencyChoiceState()
) : CurrencyChoiceStateDelegate by currencyChoiceState