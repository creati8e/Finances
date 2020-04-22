package serg.chuprin.finances.feature.dashboard.domain.model

import serg.chuprin.finances.core.api.domain.model.MoneyAccount
import java.math.BigDecimal
import java.util.*

/**
 * Created by Sergey Chuprin on 22.04.2020.
 *
 * Immutable wrapper for SortedMap<MoneyAccount, BigDecimal> with predefined comparator.
 * It keeps [MoneyAccount] sorted by name ascending.
 */
class DashboardMoneyAccounts :
    SortedMap<MoneyAccount, BigDecimal> by TreeMap<MoneyAccount, BigDecimal>(
        ACCOUNTS_COMPARATOR
    ) {

    companion object {
        private val ACCOUNTS_COMPARATOR =
            compareByDescending(MoneyAccount::isFavorite).thenComparing(MoneyAccount::name)
    }

    fun add(moneyAccount: MoneyAccount, balance: BigDecimal): DashboardMoneyAccounts {
        return DashboardMoneyAccounts().apply {
            putAll(this@DashboardMoneyAccounts)
            put(moneyAccount, balance)
        }
    }
}