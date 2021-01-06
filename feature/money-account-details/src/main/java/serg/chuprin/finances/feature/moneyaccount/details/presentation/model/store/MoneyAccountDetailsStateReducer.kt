package serg.chuprin.finances.feature.moneyaccount.details.presentation.model.store

import serg.chuprin.finances.core.mvi.reducer.StoreStateReducer

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
class MoneyAccountDetailsStateReducer :
    StoreStateReducer<MoneyAccountDetailsEffect, MoneyAccountDetailsState> {

    override fun invoke(
        what: MoneyAccountDetailsEffect,
        state: MoneyAccountDetailsState
    ): MoneyAccountDetailsState {
        return when (what) {
            is MoneyAccountDetailsEffect.DetailsFormatted -> {
                state.copy(
                    cells = what.cells,
                    isFavorite = what.isFavorite,
                    moneyAccount = what.moneyAccount,
                    moneyAccountName = what.moneyAccountName,
                    moneyAccountBalance = what.formattedBalance
                )
            }
        }
    }

}