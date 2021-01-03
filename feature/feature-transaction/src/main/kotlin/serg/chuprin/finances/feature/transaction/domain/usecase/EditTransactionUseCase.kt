package serg.chuprin.finances.feature.transaction.domain.usecase

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.domain.model.transaction.TransactionType
import serg.chuprin.finances.core.api.domain.model.transaction.query.TransactionsQuery
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.feature.transaction.domain.model.TransactionChosenOperation
import java.math.BigDecimal
import java.time.LocalDate
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
        date: LocalDate,
        amount: BigDecimal,
        moneyAccount: MoneyAccount,
        category: TransactionCategory?,
        operation: TransactionChosenOperation
    ) {
        val transactionType = when (operation) {
            is TransactionChosenOperation.Plain -> TransactionType.PLAIN
        }

        val transaction = transactionRepository
            .transactions(
                TransactionsQuery(
                    ownerId = ownerId,
                    transactionIds = setOf(transactionId)
                )
            )
            .first()

        val updatedTransaction = transaction.copy(
            type = transactionType,
            categoryId = category?.id,
            moneyAccountId = moneyAccount.id,
            _amount = normalizeAmount(operation, amount).toString()
        )
        transactionRepository.createOrUpdate(listOf(updatedTransaction))
    }

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
            else -> {
                amount
            }
        }
    }

}