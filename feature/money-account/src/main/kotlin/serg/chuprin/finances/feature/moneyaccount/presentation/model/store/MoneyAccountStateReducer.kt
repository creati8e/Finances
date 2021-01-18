package serg.chuprin.finances.feature.moneyaccount.presentation.model.store

import serg.chuprin.finances.core.mvi.reducer.StoreStateReducer
import serg.chuprin.finances.feature.moneyaccount.presentation.model.MoneyAccountDefaultData
import java.math.BigDecimal

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
class MoneyAccountStateReducer :
    StoreStateReducer<MoneyAccountEffect, MoneyAccountState> {

    override fun invoke(
        what: MoneyAccountEffect,
        state: MoneyAccountState
    ): MoneyAccountState {
        return when (what) {
            is MoneyAccountEffect.CurrencyChoiceStateUpdated -> {
                state.copy(currencyChoiceState = what.state)
            }
            is MoneyAccountEffect.AccountNameEntered -> {
                state.copy(
                    moneyAccountName = what.accountName,
                    savingButtonIsEnabled = isSavingButtonEnabled(
                        state.moneyAccountDefaultData,
                        state.balance,
                        what.accountName
                    )
                )
            }
            is MoneyAccountEffect.BalanceEntered -> {
                state.copy(
                    balance = what.balance,
                    savingButtonIsEnabled = isSavingButtonEnabled(
                        state.moneyAccountDefaultData,
                        what.balance,
                        state.moneyAccountName
                    )
                )
            }
            is MoneyAccountEffect.InitialStateApplied -> {
                state.copy(
                    balance = what.balance,
                    toolbarTitle = what.toolbarTitle,
                    moneyAccountName = what.accountName,
                    moneyAccountDefaultData = what.moneyAccountDefaultData,
                    currencyPickerIsClickable = what.currencyPickerIsClickable,
                    accountDeletionButtonIsVisible = what.accountDeletionButtonIsVisible
                )
            }
        }
    }

    private fun isSavingButtonEnabled(
        defaultData: MoneyAccountDefaultData?,
        balance: BigDecimal?,
        accountName: String
    ): Boolean {
        val hasValidNameAndBalance = accountName.isNotBlank() && balance != null
        if (defaultData == null) {
            return hasValidNameAndBalance
        }
        return hasValidNameAndBalance
                && (defaultData.accountName != accountName || defaultData.balance != balance)
    }

}