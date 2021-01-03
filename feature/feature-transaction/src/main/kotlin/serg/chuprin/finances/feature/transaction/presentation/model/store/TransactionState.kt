package serg.chuprin.finances.feature.transaction.presentation.model.store

import serg.chuprin.finances.feature.transaction.presentation.model.TransactionChosenCategory
import serg.chuprin.finances.feature.transaction.presentation.model.TransactionChosenDate
import java.time.LocalDate

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
data class TransactionState(
    val chosenCategory: TransactionChosenCategory = TransactionChosenCategory("", null),
    val chosenDate: TransactionChosenDate = TransactionChosenDate("", LocalDate.now())
)