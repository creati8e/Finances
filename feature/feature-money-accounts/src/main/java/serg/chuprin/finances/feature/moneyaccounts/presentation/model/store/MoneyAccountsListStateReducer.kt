package serg.chuprin.finances.feature.moneyaccounts.presentation.model.store

import serg.chuprin.finances.core.mvi.reducer.StoreStateReducer

/**
 * Created by Sergey Chuprin on 06.05.2020.
 */
class MoneyAccountsListStateReducer :
    StoreStateReducer<MoneyAccountsListEffect, MoneyAccountsListState> {

    override fun invoke(
        what: MoneyAccountsListEffect,
        state: MoneyAccountsListState
    ): MoneyAccountsListState {
        TODO("Not yet implemented")
    }

}