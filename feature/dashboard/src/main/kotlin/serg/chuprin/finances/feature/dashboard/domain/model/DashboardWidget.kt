package serg.chuprin.finances.feature.dashboard.domain.model

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.TransactionToCategory
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccountToBalance
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.feature.dashboard.setup.domain.model.DashboardWidgetType
import java.math.BigDecimal
import java.util.*

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
sealed class DashboardWidget(
    val type: DashboardWidgetType
) {

    data class MoneyAccounts(
        val moneyAccountToBalance: MoneyAccountToBalance
    ) : DashboardWidget(DashboardWidgetType.MONEY_ACCOUNTS)

    /**
     * Contains current period, balance and money statistic.
     */
    data class Balance(
        val balance: BigDecimal,
        val currency: Currency,
        val currentPeriodIncomes: BigDecimal,
        val currentPeriodExpenses: BigDecimal
    ) : DashboardWidget(DashboardWidgetType.BALANCE)

    data class PeriodSelector(
        val dataPeriod: DataPeriod
    ) : DashboardWidget(DashboardWidgetType.PERIOD_SELECTOR)

    data class RecentTransactions(
        val moneyAccounts: Map<Id, MoneyAccount>,
        val transactionToCategory: TransactionToCategory
    ) : DashboardWidget(DashboardWidgetType.RECENT_TRANSACTIONS)

    data class Categories(
        val currency: Currency,
        val pages: List<DashboardCategoriesWidgetPage>
    ) : DashboardWidget(DashboardWidgetType.CATEGORIES)

}