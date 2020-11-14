package serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store

import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceState
import serg.chuprin.finances.core.api.presentation.model.AmountInputState
import java.math.BigDecimal

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
sealed class MoneyAccountCreationEffect {

    class AccountNameEntered(
        val accountName: String
    ) : MoneyAccountCreationEffect()

    class BalanceEntered(
        val balance: BigDecimal?,
        val balanceInputState: AmountInputState
    ) : MoneyAccountCreationEffect()

    class CurrencyChoiceStateUpdated(
        val state: CurrencyChoiceState
    ) : MoneyAccountCreationEffect()

}