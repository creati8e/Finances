package serg.chuprin.finances.feature.transaction.presentation.model

import serg.chuprin.finances.feature.transaction.domain.model.TransactionChosenOperation
import java.math.BigDecimal

/**
 * Created by Sergey Chuprin on 04.01.2021.
 *
 * Represents initial data of existing transaction.
 */
class TransactionDefaultData(
    val operation: TransactionChosenOperation,
    val enteredAmount: BigDecimal?,
    val chosenMoneyAccount: TransactionChosenMoneyAccount,
    val chosenCategory: TransactionChosenCategory,
    val chosenDate: TransactionChosenDate,
)