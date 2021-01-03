package serg.chuprin.finances.feature.transaction.presentation.model.store

import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
import serg.chuprin.finances.feature.transaction.presentation.model.TransactionChosenCategory
import serg.chuprin.finances.feature.transaction.presentation.model.TransactionChosenDate
import serg.chuprin.finances.feature.transaction.presentation.model.TransactionChosenMoneyAccount
import java.time.LocalDate

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
data class TransactionState(
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