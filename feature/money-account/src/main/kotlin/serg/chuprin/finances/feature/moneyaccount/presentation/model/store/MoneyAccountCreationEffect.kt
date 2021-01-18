package serg.chuprin.finances.feature.moneyaccount.presentation.model.store

import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceState
import serg.chuprin.finances.feature.moneyaccount.presentation.model.MoneyAccountDefaultData
import java.math.BigDecimal

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
sealed class MoneyAccountCreationEffect {

    class InitialStateApplied(
        val balance: BigDecimal,
        val accountName: String,
        val toolbarTitle: String,
        val currencyPickerIsClickable: Boolean,
        val accountDeletionButtonIsVisible: Boolean,
        val moneyAccountDefaultData: MoneyAccountDefaultData?
    ) : MoneyAccountCreationEffect()

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