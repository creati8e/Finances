package serg.chuprin.finances.core.api.domain

import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import java.math.BigDecimal

/**
 * Created by Sergey Chuprin on 09.01.2021.
 */
interface TransactionAmountCalculator {

    /**
     * @return total amount of [Transaction.amount] from [transactions] with respect to multi-currency.
     */
    suspend fun calculate(
        transactions: Collection<Transaction>,
        /**
         * Indicates whether amount should be calculated
         * independently of real transaction's amount sign.
         */
        isAbsoluteAmount: Boolean
    ): BigDecimal

}