package serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store

import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceState
import java.math.BigDecimal

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
sealed class MoneyAccountCreationEffect {

    class AccountNameEntered(
        val accountName: String
    ) : MoneyAccountCreationEffect()

    class BalanceEntered(
        val balance: BigDecimal?
    ) : MoneyAccountCreationEffect()

    class CurrencyChoiceStateUpdated(
        val state: CurrencyChoiceState
    ) : MoneyAccountCreationEffect()

}