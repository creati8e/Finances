package serg.chuprin.finances.feature.transaction.presentation.model.store

import serg.chuprin.finances.core.mvi.reducer.StoreStateReducer
import serg.chuprin.finances.feature.transaction.domain.model.TransactionChosenOperation
import serg.chuprin.finances.feature.transaction.presentation.model.*
import java.math.BigDecimal

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
                state.copy(
                    chosenDate = what.chosenDate,
                    saveButtonIsEnabled = checkSaveButtonEnabledStatus(
                        state.transactionDefaultData,
                        what.chosenDate,
                        state.chosenCategory,
                        state.chosenMoneyAccount,
                        state.enteredAmount,
                        state.operation
                    )
                )
            }
            is TransactionEffect.CategoryChanged -> {
                state.copy(
                    chosenCategory = what.chosenCategory,
                    saveButtonIsEnabled = checkSaveButtonEnabledStatus(
                        state.transactionDefaultData,
                        state.chosenDate,
                        what.chosenCategory,
                        state.chosenMoneyAccount,
                        state.enteredAmount,
                        state.operation
                    )
                )
            }
            is TransactionEffect.MoneyAccountChanged -> {
                state.copy(
                    chosenMoneyAccount = what.chosenMoneyAccount,
                    saveButtonIsEnabled = checkSaveButtonEnabledStatus(
                        state.transactionDefaultData,
                        state.chosenDate,
                        state.chosenCategory,
                        what.chosenMoneyAccount,
                        state.enteredAmount,
                        state.operation
                    )
                )
            }
            is TransactionEffect.AmountEntered -> {
                state.copy(
                    enteredAmount = what.enteredAmount,
                    saveButtonIsEnabled = checkSaveButtonEnabledStatus(
                        state.transactionDefaultData,
                        state.chosenDate,
                        state.chosenCategory,
                        state.chosenMoneyAccount,
                        what.enteredAmount,
                        state.operation
                    )
                )
            }
            is TransactionEffect.OperationChanged -> {
                state.copy(
                    operation = what.operation,
                    saveButtonIsEnabled = checkSaveButtonEnabledStatus(
                        state.transactionDefaultData,
                        state.chosenDate,
                        state.chosenCategory,
                        state.chosenMoneyAccount,
                        state.enteredAmount,
                        what.operation
                    )
                )
            }
            is TransactionEffect.InitialStateFormatted -> {
                state.copy(
                    userId = what.userId,
                    operation = what.operation,
                    chosenDate = what.chosenDate,
                    enteredAmount = what.enteredAmount,
                    chosenCategory = what.chosenCategory,
                    chosenMoneyAccount = what.chosenMoneyAccount,
                    transactionDefaultData = what.transactionDefaultData,
                    transactionDeletionButtonIsVisible = what.transactionDeletionButtonIsVisible
                )
            }
        }
    }

    private fun checkSaveButtonEnabledStatus(
        transactionDefaultData: TransactionDefaultData?,
        chosenDate: TransactionChosenDate,
        chosenCategory: TransactionChosenCategory,
        chosenMoneyAccount: TransactionChosenMoneyAccount,
        enteredAmount: TransactionEnteredAmount,
        operation: TransactionChosenOperation
    ): Boolean {
        // If default data is null, then screen in transaction creation mode.
        if (transactionDefaultData == null) {
            return enteredAmount.amount != null && enteredAmount.amount != BigDecimal.ZERO
        }
        return transactionDefaultData.chosenDate.localDate != chosenDate.localDate
                || transactionDefaultData.operation != operation
                || transactionDefaultData.enteredAmount.amount != enteredAmount.amount
                || transactionDefaultData.chosenCategory.category != chosenCategory.category
                || transactionDefaultData.chosenMoneyAccount.account != chosenMoneyAccount.account
    }

}