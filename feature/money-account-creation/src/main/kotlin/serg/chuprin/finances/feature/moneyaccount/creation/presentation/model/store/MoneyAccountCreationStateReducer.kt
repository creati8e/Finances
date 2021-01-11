package serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store

import serg.chuprin.finances.core.mvi.reducer.StoreStateReducer

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
                    savingButtonIsEnabled = what.accountName.isNotBlank() && state.balance != null
                )
            }
            is MoneyAccountCreationEffect.BalanceEntered -> {
                state.copy(
                    balance = what.balance,
                    savingButtonIsEnabled = state.moneyAccountName.isNotBlank() && what.balance != null
                )
            }
        }
    }

}