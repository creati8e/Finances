package serg.chuprin.finances.feature.transaction.presentation.model.store

import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
import serg.chuprin.finances.feature.transaction.presentation.model.*
import java.math.BigDecimal
import java.time.LocalDate

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
data class TransactionState(
    val operation: TransactionChosenOperation = TransactionChosenOperation.Plain(
        PlainTransactionType.EXPENSE
    ),
    val saveButtonIsEnabled: Boolean = false,
    val enteredAmount: TransactionEnteredAmount = TransactionEnteredAmount(
        hasError = false,
        formatted = "0",
        amount = BigDecimal.ZERO
    ),
    val chosenMoneyAccount: TransactionChosenMoneyAccount = TransactionChosenMoneyAccount(
        formattedName = EMPTY_STRING,
        account = MoneyAccount.EMPTY
    ),
    val chosenCategory: TransactionChosenCategory = TransactionChosenCategory(
        formattedName = EMPTY_STRING,
        category = null
    ),
    val chosenDate: TransactionChosenDate = TransactionChosenDate(
        formatted = EMPTY_STRING,
        date = LocalDate.now()
    )
)