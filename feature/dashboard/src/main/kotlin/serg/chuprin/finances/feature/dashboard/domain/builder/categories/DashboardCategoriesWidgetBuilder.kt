package serg.chuprin.finances.feature.dashboard.domain.builder.categories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.feature.dashboard.domain.builder.DashboardWidgetBuilder
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardCategoriesWidgetPage
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import serg.chuprin.finances.feature.dashboard.domain.service.DashboardCategoriesDataService
import serg.chuprin.finances.feature.dashboard.setup.domain.model.DashboardWidgetType
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 23.04.2020.
 */
class DashboardCategoriesWidgetBuilder @Inject constructor(
    private val pageBuilder: DashboardCategoriesPageBuilder,
    private val categoriesDataService: DashboardCategoriesDataService
) : DashboardWidgetBuilder<DashboardWidget.Categories> {

    private companion object {
        private const val TOP_CATEGORIES_COUNT = 6
    }

    override fun isForType(widgetType: DashboardWidgetType): Boolean {
        return widgetType == DashboardWidgetType.CATEGORIES
    }

    override fun build(
        currentUser: User,
        currentPeriod: DataPeriod
    ): Flow<DashboardWidget.Categories> {
        return combine(
            flowOf(currentUser),
            pageFlow(currentUser, currentPeriod, PlainTransactionType.EXPENSE),
            pageFlow(currentUser, currentPeriod, PlainTransactionType.INCOME)
        ) { user, page1, page2 ->
            DashboardWidget.Categories(user.defaultCurrency, listOf(page1, page2))
        }
    }

    private fun pageFlow(
        currentUser: User,
        currentPeriod: DataPeriod,
        transactionType: PlainTransactionType
    ): Flow<DashboardCategoriesWidgetPage> {
        return categoriesDataService
            .dataFlow(
                currentUser = currentUser,
                currentPeriod = currentPeriod,
                transactionType = transactionType
            )
            .map { transactionsListForCategory ->
                pageBuilder.build(
                    transactionType = transactionType,
                    topCategoriesCount = TOP_CATEGORIES_COUNT,
                    categoryToTransactionsList = transactionsListForCategory
                )
            }
    }

}