package serg.chuprin.finances.feature.transaction.domain.usecase

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.Category
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.domain.model.transaction.TransactionType
import serg.chuprin.finances.core.api.domain.model.transaction.query.TransactionsQuery
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.feature.transaction.domain.model.TransactionChosenOperation
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 03.01.2021.
 */
class EditTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {

    suspend fun execute(
        transactionId: Id,
        ownerId: Id,
        newDate: LocalDate,
        amount: BigDecimal,
        moneyAccount: MoneyAccount,
        category: Category?,
        operation: TransactionChosenOperation,
        existingDateTime: LocalDateTime
    ) {
        val transactionType = when (operation) {
            is TransactionChosenOperation.Plain -> TransactionType.PLAIN
        }

        val updatedTransaction = getTransaction(
            ownerId = ownerId,
            transactionId = transactionId
        ).copy(
            type = transactionType,
            categoryId = category?.id,
            moneyAccountId = moneyAccount.id,
            _dateTime = adjustDateTime(newDate, existingDateTime),
            _amount = normalizeAmount(operation, amount).toString()
        )
        transactionRepository.createOrUpdate(listOf(updatedTransaction))
    }

    /**
     * If new date's day is the same as existing, use it without changing.
     * Otherwise use new date with time set to day start.
     */
    private fun adjustDateTime(
        newDate: LocalDate,
        existingDateTime: LocalDateTime
    ): LocalDateTime {
        return if (newDate == existingDateTime.toLocalDate()) {
            existingDateTime
        } else {
            newDate.atStartOfDay()
        }
    }

    private suspend fun getTransaction(
        ownerId: Id,
        transactionId: Id
    ) = transactionRepository
        .transactions(
            TransactionsQuery(
                ownerId = ownerId,
                transactionIds = setOf(transactionId)
            )
        )
        .first()

    private fun normalizeAmount(
        operation: TransactionChosenOperation.Plain,
        amount: BigDecimal
    ): BigDecimal {
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