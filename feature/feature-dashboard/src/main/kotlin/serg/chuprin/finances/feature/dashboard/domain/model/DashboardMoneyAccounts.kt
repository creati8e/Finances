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
        accountsComparator
    ) {

    companion object {
        val accountsComparator: Comparator<MoneyAccount> =
            compareByDescending(MoneyAccount::isFavorite).thenComparing(MoneyAccount::name)
    }

    fun add(moneyAccount: MoneyAccount, balance: BigDecimal): DashboardMoneyAccounts {
        return DashboardMoneyAccounts().apply {
            putAll(this@DashboardMoneyAccounts)
            put(moneyAccount, balance)
        }
    }

    override fun hashCode(): Int = entries.hashCode()

    override fun equals(other: Any?): Boolean {
        return (other as? DashboardMoneyAccounts)?.entries == entries
    }

}