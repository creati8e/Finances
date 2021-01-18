package serg.chuprin.finances.feature.moneyaccount.presentation.model.store

import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceState
import serg.chuprin.finances.feature.moneyaccount.presentation.model.MoneyAccountDefaultData
import java.math.BigDecimal

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
sealed class MoneyAccountCreationAction {

    class SetInitialState(
        val accountName: String,
        val balance: BigDecimal,
        val toolbarTitle: String,
        val currencyPickerIsClickable: Boolean,
        val accountDeletionButtonIsVisible: Boolean,
        val moneyAccountDefaultData: MoneyAccountDefaultData?
    ) : MoneyAccountCreationAction()

    class UpdateCurrencyChoiceState(
        val state: CurrencyChoiceState
    ) : MoneyAccountCreationAction()

    class ExecuteIntent(
        val intent: MoneyAccountCreationIntent
    ) : MoneyAccountCreationAction()

}