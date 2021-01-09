package serg.chuprin.finances.core.impl.domain

import serg.chuprin.finances.core.api.domain.TransactionAmountCalculator
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import java.math.BigDecimal
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 09.01.2021.
 */
internal class TransactionAmountCalculatorImpl @Inject constructor() : TransactionAmountCalculator {

    override suspend fun calculate(
        transactions: Collection<Transaction>,
        isAbsoluteAmount: Boolean
    ): BigDecimal {
        // TODO: Implement multi-currency logic.
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