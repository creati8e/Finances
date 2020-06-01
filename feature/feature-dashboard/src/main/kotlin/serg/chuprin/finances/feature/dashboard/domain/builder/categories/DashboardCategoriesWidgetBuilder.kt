package serg.chuprin.finances.feature.dashboard.domain.builder.categories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.domain.service.TransactionCategoryRetrieverService
import serg.chuprin.finances.feature.dashboard.domain.builder.DashboardWidgetBuilder
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardCategoriesWidgetPage
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import java.math.BigDecimal
import javax.inject.Inject

typealias CategoryAmounts = List<Pair<TransactionCategory?, BigDecimal>>

/**
 * Created by Sergey Chuprin on 23.04.2020.
 */
class DashboardCategoriesWidgetBuilder @Inject constructor(
    private val pageBuilder: DashboardCategoriesPageBuilder,
    private val transactionCategoryRetrieverService: TransactionCategoryRetrieverService
) : DashboardWidgetBuilder<DashboardWidget.Categories> {

    private companion object {
        private const val TOP_CATEGORIES_COUNT = 6
    }

    override fun build(
        currentUser: User,
        currentPeriod: DataPeriod
    ): Flow<DashboardWidget.Categories>? {
        return combine(
            pageFlow(currentUser, currentPeriod, PlainTransactionType.EXPENSE),
            pageFlow(currentUser, currentPeriod, PlainTransactionType.INCOME)
        ) { page1, page2 ->
            DashboardWidget.Categories(currentUser.defaultCurrency, listOf(page1, page2))
        }
    }

    private fun pageFlow(
        currentUser: User,
        currentPeriod: DataPeriod,
        transactionType: PlainTransactionType
    ): Flow<DashboardCategoriesWidgetPage> {
        return transactionCategoryRetrieverService
            .userCategoryTransactionsInPeriod(
                userId = currentUser.id,
                dataPeriod = currentPeriod,
                transactionType = transactionType
            )
            .map { categoryTransactionsMap ->
                pageBuilder.build(
                    transactionType,
                    categoryTransactionsMap,
                    TOP_CATEGORIES_COUNT
                )
            }
    }

}