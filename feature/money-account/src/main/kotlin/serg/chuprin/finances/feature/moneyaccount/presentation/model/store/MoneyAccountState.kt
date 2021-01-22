package serg.chuprin.finances.feature.moneyaccount.presentation.model.store

import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceState
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceStateDelegate
import serg.chuprin.finances.feature.moneyaccount.presentation.model.MoneyAccountDefaultData
import java.math.BigDecimal

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
data class MoneyAccountState(
    val balance: BigDecimal? = BigDecimal.ZERO,
    val toolbarTitle: String = EMPTY_STRING,
    val moneyAccountName: String = EMPTY_STRING,
    val savingButtonIsEnabled: Boolean = false,
    // TODO: Maybe move to CurrencyChoiceStore.
    val currencyPickerIsClickable: Boolean = false,
    val accountDeletionButtonIsVisible: Boolean = false,
    // Default data of existing account, if present.
    // Used only for checking if some data is changed for existing account.
    val moneyAccountDefaultData: MoneyAccountDefaultData? = null,
    val currencyChoiceState: CurrencyChoiceState = CurrencyChoiceState()
) : CurrencyChoiceStateDelegate by currencyChoiceState