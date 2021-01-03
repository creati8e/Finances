package serg.chuprin.finances.feature.transaction.presentation.model.store

import serg.chuprin.finances.core.mvi.reducer.StoreStateReducer

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
class TransactionStateReducer : StoreStateReducer<TransactionEffect, TransactionState> {

    override fun invoke(
        what: TransactionEffect,
        state: TransactionState
    ): TransactionState {
        return when (what) {
            is TransactionEffect.DateChanged -> {
                state.copy(chosenDate = what.chosenDate)
            }
            is TransactionEffect.CategoryChanged -> {
                state.copy(chosenCategory = what.chosenCategory)
            }
            is TransactionEffect.MoneyAccountChanged -> {
                state.copy(chosenMoneyAccount = what.chosenMoneyAccount)
            }
            is TransactionEffect.AmountEntered -> {
                state.copy(
                    enteredAmount = what.enteredAmount,
                    saveButtonIsEnabled = what.isSaveButtonEnabled
                )
            }
            is TransactionEffect.OperationChanged -> {
                state.copy(operation = what.operation)
            }
        }
    }

}