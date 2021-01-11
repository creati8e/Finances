package serg.chuprin.finances.core.test.domain

import serg.chuprin.finances.core.api.domain.TransactionAmountCalculator
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import java.math.BigDecimal

/**
 * Created by Sergey Chuprin on 09.01.2021.
 */
class TransactionAmountCalculatorTestImpl : TransactionAmountCalculator {

    override suspend fun calculate(
        transactions: Collection<Transaction>,
        isAbsoluteAmount: Boolean
    ): BigDecimal {
        return transactions.fold(BigDecimal.ZERO, { acc, transaction ->
            val amount = if (isAbsoluteAmount) {
                transaction.amount.abs()
            } else {
                transaction.amount
            }
            acc + amount
        })
    }

}