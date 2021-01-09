package serg.chuprin.finances.feature.dashboard.domain.builder

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.service.MoneyAccountBalanceService
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import serg.chuprin.finances.feature.dashboard.setup.domain.model.DashboardWidgetType
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
class DashboardMoneyAccountsWidgetBuilder @Inject constructor(
    private val moneyAccountBalanceService: MoneyAccountBalanceService
) : DashboardWidgetBuilder<DashboardWidget.MoneyAccounts> {

    override fun isForType(widgetType: DashboardWidgetType): Boolean {
        return widgetType == DashboardWidgetType.MONEY_ACCOUNTS
    }

    override fun build(
        currentUser: User,
        currentPeriod: DataPeriod
    ): Flow<DashboardWidget.MoneyAccounts> {
        return moneyAccountBalanceService
            .balancesFlow(currentUser)
            .map(DashboardWidget::MoneyAccounts)
    }

}