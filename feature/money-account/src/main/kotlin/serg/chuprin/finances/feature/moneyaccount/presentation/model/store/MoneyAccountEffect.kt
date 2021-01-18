package serg.chuprin.finances.feature.moneyaccount.presentation.model.store

import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceState
import serg.chuprin.finances.feature.moneyaccount.presentation.model.MoneyAccountDefaultData
import java.math.BigDecimal

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
sealed class MoneyAccountEffect {

    class InitialStateApplied(
        val balance: BigDecimal,
        val accountName: String,
        val toolbarTitle: String,
        val currencyPickerIsClickable: Boolean,
        val accountDeletionButtonIsVisible: Boolean,
        val moneyAccountDefaultData: MoneyAccountDefaultData?
    ) : MoneyAccountEffect()

    class AccountNameEntered(
        val accountName: String
    ) : MoneyAccountEffect()

    class BalanceEntered(
        val balance: BigDecimal?
    ) : MoneyAccountEffect()

    class CurrencyChoiceStateUpdated(
        val state: CurrencyChoiceState
    ) : MoneyAccountEffect()

}