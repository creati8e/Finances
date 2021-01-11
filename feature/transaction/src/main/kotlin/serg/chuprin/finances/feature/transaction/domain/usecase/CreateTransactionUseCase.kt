package serg.chuprin.finances.feature.transaction.domain.usecase

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.Category
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.model.transaction.TransactionType
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.feature.transaction.domain.model.TransactionChosenOperation
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 03.01.2021.
 */
class CreateTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {

    fun execute(
        ownerId: Id,
        date: LocalDate,
        amount: BigDecimal,
        moneyAccount: MoneyAccount,
        category: Category?,
        operation: TransactionChosenOperation
    ) {
        val transactionType = when (operation) {
            is TransactionChosenOperation.Plain -> TransactionType.PLAIN
        }
        val transaction = Transaction(
            id = Id.createNew(),
            ownerId = ownerId,
            type = transactionType,
            categoryId = category?.id,
            _dateTime = adjustDate(date),
            moneyAccountId = moneyAccount.id,
            currencyCode = moneyAccount.currencyCode,
            _amount = normalizeAmount(operation, amount).toString()
        )
        transactionRepository.createOrUpdate(listOf(transaction))
    }

    /**
     * Take current time if date is today.
     */
    private fun adjustDate(date: LocalDate): LocalDateTime {
        return if (date == LocalDate.now()) {
            LocalDateTime.now()
        } else {
            date.atStartOfDay()
        }
    }

    private fun normalizeAmount(
        operation: TransactionChosenOperation.Plain,
        amount: BigDecimal
    ): BigDecimal? {
        return when {
            operation.isPlain() -> {
                if (operation.type == PlainTransactionType.EXPENSE) {
                    amount.negate()
                } else {
                    amount
                }
            }
            else -> amount
        }
    }

}