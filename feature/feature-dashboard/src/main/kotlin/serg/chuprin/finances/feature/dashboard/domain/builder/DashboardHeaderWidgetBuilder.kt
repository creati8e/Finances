package serg.chuprin.finances.feature.dashboard.domain.builder

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.model.DataPeriod
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.feature.dashboard.domain.MoneyCalculator
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
class DashboardHeaderWidgetBuilder @Inject constructor(
    private val moneyCalculator: MoneyCalculator
) : DashboardWidgetBuilder<DashboardWidget.Header> {

    override fun build(
        currentUser: User,
        currentPeriod: DataPeriod
    ): Flow<DashboardWidget.Header> {
        return combine(
            flowOf(currentUser),
            flowOf(currentPeriod),
            moneyCalculator.calculateBalance(currentUser.id),
            moneyCalculator.calculateIncomes(currentUser.id, currentPeriod),
            moneyCalculator.calculateExpenses(currentUser.id, currentPeriod)
        ) { user, period, balance, incomes, expenses ->
            DashboardWidget.Header(
                balance = balance,
                dataPeriod = period,
                currency = user.defaultCurrency,
                currentPeriodIncomes = incomes,
                currentPeriodExpenses = expenses
            )
        }
    }

}