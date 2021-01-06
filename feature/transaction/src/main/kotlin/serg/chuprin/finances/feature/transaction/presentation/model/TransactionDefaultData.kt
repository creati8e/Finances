package serg.chuprin.finances.feature.transaction.presentation.model

import serg.chuprin.finances.feature.transaction.domain.model.TransactionChosenOperation

/**
 * Created by Sergey Chuprin on 04.01.2021.
 *
 * Represents initial data of existing transaction.
 */
class TransactionDefaultData(
    val operation: TransactionChosenOperation,
    val enteredAmount: TransactionEnteredAmount,
    val chosenMoneyAccount: TransactionChosenMoneyAccount,
    val chosenCategory: TransactionChosenCategory,
    val chosenDate: TransactionChosenDate,
)