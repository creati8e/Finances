package serg.chuprin.finances.feature.transaction.presentation.model.store

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.feature.transaction.domain.model.TransactionChosenOperation
import serg.chuprin.finances.feature.transaction.presentation.model.TransactionChosenCategory
import serg.chuprin.finances.feature.transaction.presentation.model.TransactionChosenDate
import serg.chuprin.finances.feature.transaction.presentation.model.TransactionChosenMoneyAccount
import serg.chuprin.finances.feature.transaction.presentation.model.TransactionDefaultData
import java.math.BigDecimal

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
        val enteredAmount: BigDecimal?
    ) : TransactionEffect()

    class OperationChanged(
        val operation: TransactionChosenOperation
    ) : TransactionEffect()

    class InitialStateFormatted(
        val userId: Id,
        val chosenDate: TransactionChosenDate,
        val operation: TransactionChosenOperation,
        val enteredAmount: BigDecimal?,
        val chosenCategory: TransactionChosenCategory,
        val chosenMoneyAccount: TransactionChosenMoneyAccount,
        val transactionDefaultData: TransactionDefaultData?,
        val transactionDeletionButtonIsVisible: Boolean
    ) : TransactionEffect()

}