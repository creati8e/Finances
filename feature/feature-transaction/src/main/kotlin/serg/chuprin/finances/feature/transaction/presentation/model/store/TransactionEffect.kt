package serg.chuprin.finances.feature.transaction.presentation.model.store

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
        val isSaveButtonEnabled: Boolean,
        val enteredAmount: TransactionEnteredAmount
    ) : TransactionEffect()

    class OperationChanged(
        val operation: TransactionChosenOperation
    ) : TransactionEffect()

}