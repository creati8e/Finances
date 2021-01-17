package serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store

import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceState
import java.math.BigDecimal
import java.util.*

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
sealed class MoneyAccountCreationAction {

    class SetInitialStateForExistingAccount(
        val accountName: String,
        val balance: BigDecimal,
        val currency: Currency
    ) : MoneyAccountCreationAction()

    class UpdateCurrencyChoiceState(
        val state: CurrencyChoiceState
    ) : MoneyAccountCreationAction()

    class ExecuteIntent(
        val intent: MoneyAccountCreationIntent
    ) : MoneyAccountCreationAction()

}