package serg.chuprin.finances.feature.dashboard.domain.builder

import kotlinx.coroutines.flow.*
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.moneyaccount.query.MoneyAccountsQuery
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.model.transaction.query.TransactionsQuery
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.service.TransactionCategoryRetrieverService
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import serg.chuprin.finances.feature.dashboard.domain.repository.DashboardDataPeriodRepository
import serg.chuprin.finances.feature.dashboard.setup.domain.model.DashboardWidgetType
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
class DashboardRecentTransactionsWidgetBuilder @Inject constructor(
    private val moneyAccountRepository: MoneyAccountRepository,
    private val dataPeriodRepository: DashboardDataPeriodRepository,
    private val transactionCategoryRetrieverService: TransactionCategoryRetrieverService
) : DashboardWidgetBuilder<DashboardWidget.RecentTransactions> {

    private companion object {
        private const val RECENT_TRANSACTIONS_COUNT = 4
    }

    override fun isForType(widgetType: DashboardWidgetType): Boolean {
        return widgetType == DashboardWidgetType.RECENT_TRANSACTIONS
    }

    override fun build(
        currentUser: User,
        currentPeriod: DataPeriod
    ): Flow<DashboardWidget.RecentTransactions>? {
        if (dataPeriodRepository.defaultDataPeriod != currentPeriod) {
            return null
        }
        return combine(
            flowOf(currentUser),
            transactionCategoryRetrieverService
                .transactionsFlow(
                    currentUser.id,
                    TransactionsQuery(
                        ownerId = currentUser.id,
                        endDate = currentPeriod.endDate,
                        limit = RECENT_TRANSACTIONS_COUNT,
                        startDate = currentPeriod.startDate,
                        sortOrder = TransactionsQuery.SortOrder.DATE_DESC
                    )
                ),
            ::Pair
        ).flatMapLatest { (user, transactions) ->
            val moneyAccountIds = transactions.keys.mapTo(
                mutableSetOf(),
                Transaction::moneyAccountId
            )
            combine(
                getMoneyAccounts(user, moneyAccountIds),
                flowOf(transactions),
                DashboardWidget::RecentTransactions
            )
        }
    }

    private fun getMoneyAccounts(
        user: User,
        moneyAccountIds: MutableSet<Id>
    ): Flow<Map<Id, MoneyAccount>> {
        return moneyAccountRepository
            .accountsFlow(
                MoneyAccountsQuery(
                    ownerId = user.id,
                    accountIds = moneyAccountIds
                )
            )
            .map { moneyAccounts ->
                moneyAccounts.associateBy(MoneyAccount::id)
            }
    }

}