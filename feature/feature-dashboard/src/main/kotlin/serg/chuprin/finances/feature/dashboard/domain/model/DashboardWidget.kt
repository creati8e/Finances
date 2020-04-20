package serg.chuprin.finances.feature.dashboard.domain.model

import serg.chuprin.finances.core.api.domain.model.DataPeriod
import serg.chuprin.finances.core.api.domain.model.Transaction
import serg.chuprin.finances.core.api.domain.model.TransactionCategoryWithParent
import java.math.BigDecimal
import java.util.*

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
sealed class DashboardWidget(val type: Type) {

    enum class Type(val order: Int) {
        HEADER(1),
        RECENT_TRANSACTIONS(2)
    }

    /**
     * Contains current period, balance and money statistic.
     */
    data class Header(
        val dataPeriod: DataPeriod,
        val balance: BigDecimal,
        val currency: Currency,
        val currentPeriodIncomes: BigDecimal,
        val currentPeriodExpenses: BigDecimal
    ) : DashboardWidget(type = Type.HEADER)

    data class RecentTransactions(
        val currency: Currency,
        val transactionWithCategoryMap: Map<Transaction, TransactionCategoryWithParent?>
    ) : DashboardWidget(type = Type.RECENT_TRANSACTIONS)

}