package serg.chuprin.finances.feature.moneyaccount.details.domain.model

import serg.chuprin.finances.core.api.domain.model.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
data class MoneyAccountDetails(
    val balance: BigDecimal = BigDecimal.ZERO,
    val moneyAccount: MoneyAccount = MoneyAccount.EMPTY,
    val transactionsGroupedByDay: SortedMap<LocalDate, List<Map.Entry<Transaction, TransactionCategoryWithParent?>>> = TreeMap()
)