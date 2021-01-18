package serg.chuprin.finances.core.api.domain

import serg.chuprin.finances.core.api.domain.model.Id
import java.math.BigDecimal

/**
 * Created by Sergey Chuprin on 18.01.2021.
 */
interface MoneyAccountBalanceCalculator {

    suspend fun calculate(moneyAccountId: Id): BigDecimal

}