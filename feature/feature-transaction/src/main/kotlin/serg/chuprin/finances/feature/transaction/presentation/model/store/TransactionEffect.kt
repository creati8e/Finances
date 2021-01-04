package serg.chuprin.finances.feature.transaction.presentation.model.store

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.feature.transaction.domain.model.TransactionChosenOperation
import serg.chuprin.finances.feature.transaction.presentation.model.*

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
sealed class TransactionEffect {

    class DateChanged(
        val chosenDate: TransactionChosenDate
    ) : TransactionEffect()

    class MoneyAccountChanged(
        val chosenMoneyAccount: TransactionChosenMoneyAccount
    ) : TransactionEffect()

    class CategoryChanged(
        val chosenCategory: TransactionChosenCategory
    ) : TransactionEffect()

    class AmountEntered(
        val enteredAmount: TransactionEnteredAmount
    ) : TransactionEffect()

    class OperationChanged(
        val operation: TransactionChosenOperation
    ) : TransactionEffect()

    class InitialStateFormatted(
        val userId: Id,
        val chosenDate: TransactionChosenDate,
        val operation: TransactionChosenOperation,
        val enteredAmount: TransactionEnteredAmount,
        val chosenCategory: TransactionChosenCategory,
        val chosenMoneyAccount: TransactionChosenMoneyAccount,
        val transactionDefaultData: TransactionDefaultData?
    ) : TransactionEffect()

}