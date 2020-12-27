package serg.chuprin.finances.feature.dashboard.domain.builder

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.transaction.query.TransactionsQuery
import serg.chuprin.finances.core.api.domain.service.TransactionCategoryRetrieverService
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import serg.chuprin.finances.feature.dashboard.domain.repository.DashboardDataPeriodRepository
import serg.chuprin.finances.feature.dashboard.setup.presentation.domain.model.DashboardWidgetType
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
class DashboardRecentTransactionsWidgetBuilder @Inject constructor(
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
        return transactionCategoryRetrieverService
            .transactionsFlow(
                currentUser.id,
                TransactionsQuery(
                    userId = currentUser.id,
                    endDate = currentPeriod.endDate,
                    limit = RECENT_TRANSACTIONS_COUNT,
                    startDate = currentPeriod.startDate,
                    sortOrder = TransactionsQuery.SortOrder.DATE_DESC
                )
            )
            .map(DashboardWidget::RecentTransactions)
    }

}