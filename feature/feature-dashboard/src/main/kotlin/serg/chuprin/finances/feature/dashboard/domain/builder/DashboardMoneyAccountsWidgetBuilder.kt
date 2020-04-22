package serg.chuprin.finances.feature.dashboard.domain.builder

import kotlinx.coroutines.flow.*
import serg.chuprin.finances.core.api.domain.model.DataPeriod
import serg.chuprin.finances.core.api.domain.model.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.feature.dashboard.domain.MoneyCalculator
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardMoneyAccounts
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
class DashboardMoneyAccountsWidgetBuilder @Inject constructor(
    private val calculator: MoneyCalculator,
    private val moneyAccountRepository: MoneyAccountRepository
) : DashboardWidgetBuilder<DashboardWidget.MoneyAccounts> {

    override fun build(
        currentUser: User,
        currentPeriod: DataPeriod
    ): Flow<DashboardWidget.MoneyAccounts> {
        return moneyAccountRepository
            .userAccountsFlow(currentUser.id)
            .flatMapLatest { moneyAccounts ->
                calculateBalance(moneyAccounts)
            }
            .map { moneyAccounts ->
                DashboardWidget.MoneyAccounts(moneyAccounts)
            }
    }

    @Suppress("MoveLambdaOutsideParentheses")
    private fun calculateBalance(moneyAccounts: List<MoneyAccount>): Flow<DashboardMoneyAccounts> {
        return moneyAccounts
            .map { account ->
                combine(
                    flowOf(account),
                    calculator.calculateMoneyAccountBalance(account.id),
                    { acc, balance -> acc to balance }
                )
            }
            .merge()
            .scan(DashboardMoneyAccounts(), { accounts, (account, balance) ->
                accounts.add(account, balance)
            })
    }

}