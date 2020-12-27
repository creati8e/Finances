package serg.chuprin.finances.feature.moneyaccount.details.domain.model

import serg.chuprin.finances.core.api.domain.model.TransactionsGroupedByDay
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import java.math.BigDecimal
import java.util.*

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
data class MoneyAccountDetails(
    val balance: BigDecimal = BigDecimal.ZERO,
    val moneyAccount: MoneyAccount = MoneyAccount.EMPTY,
    val transactionsGroupedByDay: TransactionsGroupedByDay = TreeMap()
)