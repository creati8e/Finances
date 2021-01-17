package serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store

import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceState
import serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.MoneyAccountDefaultData
import java.math.BigDecimal

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
sealed class MoneyAccountCreationAction {

    object MakeCurrencyPickerClickable : MoneyAccountCreationAction()

    class SetInitialStateForExistingAccount(
        val accountName: String,
        val balance: BigDecimal,
        val moneyAccountDefaultData: MoneyAccountDefaultData
    ) : MoneyAccountCreationAction()

    class UpdateCurrencyChoiceState(
        val state: CurrencyChoiceState
    ) : MoneyAccountCreationAction()

    class ExecuteIntent(
        val intent: MoneyAccountCreationIntent
    ) : MoneyAccountCreationAction()

}