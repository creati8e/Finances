package serg.chuprin.finances.core.impl.domain

import serg.chuprin.finances.core.api.domain.MoneyAccountBalanceCalculator
import serg.chuprin.finances.core.api.domain.TransactionAmountCalculator
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.transaction.query.TransactionsQuery
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import java.math.BigDecimal
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 18.01.2021.
 */
internal class MoneyAccountBalanceCalculatorImpl @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val amountCalculator: TransactionAmountCalculator
) : MoneyAccountBalanceCalculator {

    override suspend fun calculate(moneyAccountId: Id): BigDecimal {
        val query = TransactionsQuery(moneyAccountIds = setOf(moneyAccountId))
        val transactions = transactionRepository.transactions(query)
        return amountCalculator.calculate(transactions, isAbsoluteAmount = false)
    }

}