package serg.chuprin.finances.feature.transaction.presentation.model.store

import serg.chuprin.finances.core.mvi.reducer.StoreStateReducer
import serg.chuprin.finances.feature.transaction.domain.model.TransactionChosenOperation
import serg.chuprin.finances.feature.transaction.presentation.model.TransactionChosenCategory
import serg.chuprin.finances.feature.transaction.presentation.model.TransactionChosenDate
import serg.chuprin.finances.feature.transaction.presentation.model.TransactionChosenMoneyAccount
import serg.chuprin.finances.feature.transaction.presentation.model.TransactionDefaultData
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
                    currencySymbol = what.chosenMoneyAccount.account.currency.symbol,
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
                    currencySymbol = what.chosenMoneyAccount.account.currency.symbol,
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
        enteredAmount: BigDecimal?,
        operation: TransactionChosenOperation
    ): Boolean {
        // If default data is null, then screen in transaction creation mode.
        if (transactionDefaultData == null) {
            return enteredAmount != null && enteredAmount != BigDecimal.ZERO
        }
        return transactionDefaultData.localDate != chosenDate.localDate
                || transactionDefaultData.operation != operation
                || transactionDefaultData.enteredAmount != enteredAmount
                || transactionDefaultData.category != chosenCategory.category
                || transactionDefaultData.moneyAccount != chosenMoneyAccount.account
    }

}