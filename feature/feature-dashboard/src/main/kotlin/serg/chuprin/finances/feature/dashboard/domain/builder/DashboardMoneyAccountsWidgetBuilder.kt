package serg.chuprin.finances.feature.dashboard.domain.builder

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.domain.model.DashboardWidget
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.service.MoneyAccountService
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
class DashboardMoneyAccountsWidgetBuilder @Inject constructor(
    private val moneyAccountService: MoneyAccountService
) : DashboardWidgetBuilder<DashboardWidget.MoneyAccounts> {

    override fun build(
        currentUser: User,
        currentPeriod: DataPeriod
    ): Flow<DashboardWidget.MoneyAccounts> {
        return moneyAccountService
            .moneyAccountBalancesFlow(currentUser)
            .map { moneyAccounts ->
                DashboardWidget.MoneyAccounts(moneyAccounts)
            }
    }

}