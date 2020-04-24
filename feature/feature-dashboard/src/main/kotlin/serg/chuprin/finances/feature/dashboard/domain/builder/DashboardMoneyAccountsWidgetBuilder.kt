package serg.chuprin.finances.feature.dashboard.domain.builder

import kotlinx.coroutines.flow.*
import serg.chuprin.finances.core.api.domain.model.DataPeriod
import serg.chuprin.finances.core.api.domain.model.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.core.api.extensions.amount
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardMoneyAccounts
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
class DashboardMoneyAccountsWidgetBuilder @Inject constructor(
    private val transactionRepository: TransactionRepository,
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
        val flows = moneyAccounts.map { account ->
            combine(
                flowOf(account),
                transactionRepository.moneyAccountTransactionsFlow(account.id),
                { acc, transactions -> acc to transactions.amount }
            )
        }
        return combine(flows, { array ->
            array.fold(DashboardMoneyAccounts(), { accounts, (account, balance) ->
                accounts.add(account, balance)
            })
        })
    }

}