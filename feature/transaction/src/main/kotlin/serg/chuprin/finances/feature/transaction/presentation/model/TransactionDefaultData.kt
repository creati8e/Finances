package serg.chuprin.finances.feature.transaction.presentation.model

import serg.chuprin.finances.core.api.domain.model.category.Category
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.feature.transaction.domain.model.TransactionChosenOperation
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Created by Sergey Chuprin on 04.01.2021.
 *
 * Represents initial data of existing transaction.
 */
class TransactionDefaultData(
    val operation: TransactionChosenOperation,
    val enteredAmount: BigDecimal?,
    val moneyAccount: MoneyAccount,
    val category: Category?,
    /**
     * Original datetime.
     */
    val dateTime: LocalDateTime,
) {

    /**
     * LocalDate retrieved from original datetime. Used for checking if date is changed.
     */
    val localDate: LocalDate = dateTime.toLocalDate()

}