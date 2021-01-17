package serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store

import serg.chuprin.finances.core.mvi.reducer.StoreStateReducer
import serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.MoneyAccountDefaultData
import java.math.BigDecimal

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
class MoneyAccountCreationStateReducer :
    StoreStateReducer<MoneyAccountCreationEffect, MoneyAccountCreationState> {

    override fun invoke(
        what: MoneyAccountCreationEffect,
        state: MoneyAccountCreationState
    ): MoneyAccountCreationState {
        return when (what) {
            is MoneyAccountCreationEffect.CurrencyChoiceStateUpdated -> {
                state.copy(currencyChoiceState = what.state)
            }
            is MoneyAccountCreationEffect.AccountNameEntered -> {
                state.copy(
                    moneyAccountName = what.accountName,
                    savingButtonIsEnabled = isSavingButtonEnabled(
                        state.moneyAccountDefaultData,
                        state.balance,
                        what.accountName
                    )
                )
            }
            is MoneyAccountCreationEffect.BalanceEntered -> {
                state.copy(
                    balance = what.balance,
                    savingButtonIsEnabled = isSavingButtonEnabled(
                        state.moneyAccountDefaultData,
                        what.balance,
                        state.moneyAccountName
                    )
                )
            }
            is MoneyAccountCreationEffect.InitialStateForExistingAccountFormatted -> {
                state.copy(
                    balance = what.balance,
                    moneyAccountName = what.accountName,
                    moneyAccountDefaultData = what.moneyAccountDefaultData
                )
            }
        }
    }

    private fun isSavingButtonEnabled(
        defaultData: MoneyAccountDefaultData?,
        balance: BigDecimal?,
        accountName: String
    ): Boolean {
        if (defaultData == null) {
            return accountName.isNotBlank() && balance != null
        }
        return defaultData.accountName != accountName || defaultData.balance != balance
    }

}