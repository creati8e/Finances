package serg.chuprin.finances.feature.transaction.presentation.model.store

import serg.chuprin.finances.feature.transaction.presentation.model.TransactionChosenCategory
import serg.chuprin.finances.feature.transaction.presentation.model.TransactionChosenDate
import serg.chuprin.finances.feature.transaction.presentation.model.TransactionChosenMoneyAccount
import serg.chuprin.finances.feature.transaction.presentation.model.TransactionEnteredAmount

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

}